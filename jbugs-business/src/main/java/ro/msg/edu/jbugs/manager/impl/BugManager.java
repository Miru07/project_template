package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.BugViewDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.entity.types.StatusType;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.helpers.StatusHelper;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import ro.msg.edu.jbugs.validators.BugValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Manager class for CRUD actions on {@link Bug}, {@link BugDTO} & {@link BugAttachmentWrapperDTO} objects.
 */
@Stateless
//@Interceptors(TimeInterceptors.class)
public class BugManager implements BugManagerRemote {
    @EJB
    BugDao bugDao;
    @EJB
    UserDao userDao;
    @EJB
    AttachmentDao attachmentDao;
    @EJB
    private NotificationManagerRemote notificationManager;
    @EJB
    UserManagerRemote userManager;

//    @Override
//    public List<BugDTO> findBugsCreatedBy(UserDTO userDTO) {
//        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
//        List<Bug> bugs = bugDao.findBugCreatedBy(user);
//
//        return bugs.stream().map(BugDTOEntityMapper::getBugDTO).collect(Collectors.toList());
//    }

    /**
     * @param bugID is the id of the {@link Bug} to be closed
     * @return a {@link BugDTO} object with the status changed to "CLOSED"
     * @throws BusinessException if the status of the {@link Bug} object cannot be changed to "CLOSED"
     * @author Miruna Dinu
     */
    @Override
    public BugDTO closeBug(int bugID) throws BusinessException{

        Bug bug = bugDao.getBugByID(bugID);
        List<StatusType> statusToClose = StatusHelper.getStatusesToClose();

        if(statusToClose.contains(StatusType.valueOf(bug.getStatus()))){

            StatusType oldStatus = StatusType.valueOf(bug.getStatus());
            Bug updatedBug = this.bugDao.updateBugStatus(StatusType.CLOSED.name(), bugID);
            notificationManager.insertBugStatusUpdatedNotification(BugDTOEntityMapper.getBugDTO(updatedBug), oldStatus);
            notificationManager.insertClosedBugNotification(BugDTOEntityMapper.getBugDTO(updatedBug));
            return BugDTOEntityMapper.getBugDTO(updatedBug);
        }
        else{
            throw new BusinessException("msg-242", "Cannot close bug");
        }
    }

    /**
     * @return a list of {@link Bug} objects with all the bugs from the database
     * @author Miruna Dinu
     */
    @Override
    public List<BugDTO> getAllBugs() {
        List<Bug> bugList = bugDao.getAllBugs();

        return BugDTOEntityMapper.getBugDTOList(bugList);
    }

    @Override
    public BugDTO getBugById(Integer id) {
        Bug bug = bugDao.getBugByID(id);
        return BugDTOEntityMapper.getBugDTO(bug);
    }

    /**
     * The function breaks and maps the {@link BugAttachmentWrapperDTO} object to an {@link Bug} and an
     * {@link Attachment} object respectively.
     * The function searches for both {@link User} objects and sets them to the {@link Bug} object.
     * The {@link Bug} object is persisted into the database. It comes with a set ID. This new object will be set to
     * the {@link Attachment} object and then persisted into the database.
     *
     * @param wrapperDTO is an {@link BugAttachmentWrapperDTO} object that contains
     *                   the Bug details and Attachment details in a DTO format.
     * @throws {@link BusinessException} if the request is incomplete/incorrect.
     * @author Sebastian Maier
     */
    @Override
    public BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO, Integer createdID) throws BusinessException {
        if (wrapperDTO.getBug() == null || wrapperDTO.getAttachment() == null || wrapperDTO.getToken() == null) {
            throw new BusinessException("msg-500", "An Entity is empty.");
        }
        if (wrapperDTO.getBug().getASSIGNED_ID() == null) {
            throw new BusinessException("msg-501", "Needs to be Assigned");
        }

        Bug bugToPersist = BugDTOEntityMapper.getBug(wrapperDTO.getBug());

        if (!BugValidator.validate(bugToPersist)) {
            throw new BusinessException("msg-502", "Entity is not valid.");
        }

        User createdUserToSet = userDao.findUser(createdID);
        User assignedUserToSet = userDao.findUser(bugToPersist.getASSIGNED_ID().getID());
        if (createdUserToSet == null || assignedUserToSet == null) {
            throw new BusinessException("msg-503", "User does not exist");
        }
        bugToPersist.setCREATED_ID(createdUserToSet);
        bugToPersist.setASSIGNED_ID(assignedUserToSet);
        // We change the Status from NEW to OPEN.
        bugToPersist.setStatus("OPEN");

        Bug persistedBugWithID = bugDao.insert(bugToPersist);

        if (persistedBugWithID.getID().equals(0) || persistedBugWithID.getID() == null) {
            throw new BusinessException("msg-504", "Bug could not be added");
        }
        if (!(wrapperDTO.getAttachment().getAttContent() == null || wrapperDTO.getAttachment().getAttContent().length == 0)) {
            Attachment attachmentToPersist = AttachmentDTOEntityMapper.getAttachment(wrapperDTO.getAttachment());
            attachmentToPersist.setBugID(persistedBugWithID);

            Attachment persistedAttachmentWithID = attachmentDao.insert(attachmentToPersist);
            if (persistedAttachmentWithID.getID().equals(0) || persistedAttachmentWithID.getID() == null) {
                throw new BusinessException("msg-505", "Attachment could not be added");
            } else return new BugAttachmentWrapperDTO(BugDTOEntityMapper.getBugDTO(persistedBugWithID),
                    AttachmentDTOEntityMapper.getAttachmentDTO(persistedAttachmentWithID), wrapperDTO.getToken());
        }
        return new BugAttachmentWrapperDTO(BugDTOEntityMapper.getBugDTO(persistedBugWithID), wrapperDTO.getAttachment(),
                wrapperDTO.getToken());

    }

    /**
     * @param newStatus is a {@link String} that contains the new status of the bug
     * @param oldStatus is a {@link String} that contains the old status of the bug
     * @return {@link Boolean} that is true if the Status is changeable, false if not.
     * @author Miruna Dinu
     */
    private boolean updateBugStatus(String newStatus, String oldStatus) throws BusinessException {
        List<StatusType> allowedStatuses = StatusHelper.getStatusForUpdate(StatusType.valueOf(oldStatus));

        return (!allowedStatuses.isEmpty() && allowedStatuses.contains(StatusType.valueOf(newStatus)));
    }

    /**
     * The function breaks and maps the {@link BugDTO} object to an {@link Bug} object.
     * We check for the User who has created the request, with the help of its ID.
     * If that user exists and has the permission to update Bugs, we check to see if
     * the Bug to be updated exists in the database with the help of its ID.
     * If it exists, we update it and persist it.
     *
     * @param requestUserID is the ID of the User who makes the request
     * @param bugID         is the ID of the Bug who the function updates
     * @param bugToUpdate   contains the new attributes of the Bug to be updated
     * @return {@link BugDTO} object that is the updated entity in the database.
     * @throws {@link BusinessException} if the user does not exist/doesn't have the rights, the Bug doesn't exist
     *                or the details to be updated are incomplete/incorrect.
     * @author Miruna Dinu & Sebastian Maier
     */
    @Override
    public BugDTO updateBug(Integer requestUserID, Integer bugID, BugDTO bugToUpdate) throws BusinessException {
        if (requestUserID == null || requestUserID == 0 || bugID == null || bugID == 0 || bugToUpdate == null) {
            throw new BusinessException("msg-600", "Contents are empty");
        }
        User requestUser = userDao.findUser(requestUserID);
        if (requestUser == null) {
            throw new BusinessException("msg-601", "User does not exist");
        }
        boolean hasUpdatePermission = userDao.getPermissionsOfUser(requestUserID).
                contains(PermissionType.BUG_MANAGEMENT);
        if (!hasUpdatePermission) {
            throw new BusinessException("msg-602", "User does not have permission");
        }

        Bug bugInDatabase = bugDao.getBugByID(bugID);
        if (bugInDatabase == null) {
            throw new BusinessException("msg-603", "Bug does not exist");
        }
        if (bugToUpdate.getASSIGNED_ID() == null) {
            throw new BusinessException("msg-604", "Bug has assigned Users that are empty.");
        }
        Bug bugMappedToUpdate = BugDTOEntityMapper.getBugWithUserCreatedAndAssigned(bugToUpdate);

        User assignedUserFromBugToUpdate = userDao.findUser(bugMappedToUpdate.getASSIGNED_ID().getID());

        if (assignedUserFromBugToUpdate == null) {
            throw new BusinessException("msg-605", "User from updated Bug does not exist in database.");
        }
        if (!BugValidator.validate(bugInDatabase)) {
            throw new BusinessException("msg-606", "Updated Bug is not valid!");
        }

        StatusType oldStatus = StatusType.valueOf(bugInDatabase.getStatus());

        if (updateBugStatus(bugMappedToUpdate.getStatus().toUpperCase(), bugInDatabase.getStatus())) {
            bugInDatabase.setStatus(bugMappedToUpdate.getStatus().toUpperCase());
        }

        boolean justStatusUpdate = justStatusUpdated(bugID, bugToUpdate);


        bugInDatabase.setTitle(bugMappedToUpdate.getTitle());
        bugInDatabase.setDescription(bugMappedToUpdate.getDescription());
        bugInDatabase.setVersion(bugMappedToUpdate.getVersion());
        bugInDatabase.setTargetDate(bugMappedToUpdate.getTargetDate());
        bugInDatabase.setFixedVersion(bugMappedToUpdate.getFixedVersion());
        bugInDatabase.setSeverity(bugMappedToUpdate.getSeverity().toUpperCase());
        bugInDatabase.setASSIGNED_ID(bugMappedToUpdate.getASSIGNED_ID());

        if (justStatusUpdate)
            notificationManager.insertBugStatusUpdatedNotification(BugDTOEntityMapper.getBugDTO(bugInDatabase), oldStatus);
        else
            notificationManager.insertBugUpdatedNotification(BugDTOEntityMapper.getBugDTO(bugInDatabase));
        return BugDTOEntityMapper.getBugDTO(bugInDatabase);
    }

    public boolean justStatusUpdated(Integer bugID, BugDTO bugToUpdate) {
        Bug bugInDatabase = bugDao.getBugByID(bugID);
        if (!bugInDatabase.getStatus().equals(bugToUpdate.getStatus()) &&
                bugInDatabase.getID().equals(bugToUpdate.getID()) &&
                bugInDatabase.getTitle().equals(bugToUpdate.getTitle()) &&
                bugInDatabase.getDescription().equals(bugToUpdate.getDescription()) &&
                bugInDatabase.getVersion().equals(bugToUpdate.getVersion()) &&
                bugInDatabase.getTargetDate().equals(bugToUpdate.getTargetDate()) &&
                bugInDatabase.getStatus().equals(bugToUpdate.getStatus()) &&
                bugInDatabase.getFixedVersion().equals(bugToUpdate.getFixedVersion()) &&
                bugInDatabase.getSeverity().equals(bugToUpdate.getSeverity()) &&
                bugInDatabase.getCREATED_ID().equals(UserDTOEntityMapper.getUserFromUserDTO(bugToUpdate.getCREATED_ID())) &&
                bugInDatabase.getASSIGNED_ID().equals(UserDTOEntityMapper.getUserFromUserDTO(bugToUpdate.getASSIGNED_ID()))
        ) {
            return true;
        }
        return false;
    }


    @Override
    public BugViewDTO getBugViewDTO(){

        List<BugDTO> bugDTOList = this.getAllBugs();
        List<UserDTO> userDTOList = this.userManager.findAllUsers();
        return new BugViewDTO(bugDTOList, userDTOList);
    }
}
