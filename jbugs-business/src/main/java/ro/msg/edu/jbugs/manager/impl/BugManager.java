package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.StatusType;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.helpers.StatusHelper;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import ro.msg.edu.jbugs.validators.BugValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<BugDTO> findBugsCreatedBy(UserDTO userDTO) {
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        List<Bug> bugs = bugDao.findBugCreatedBy(user);

        return bugs.stream().map(BugDTOEntityMapper::getBugDTO).collect(Collectors.toList());
    }

    /**
     * @param newStatus is a {@link String} that contains the new status of the bug with the bugID id
     * @param bugID     is a {@link Integer} that contains the bug id whose status is to be changed
     * @return {@link Bug} object with the new status
     * @throws BusinessException if newStatus contains a wrong string or no available statuses are found
     * @author Miruna Dinu
     */
    @Override
    public BugDTO updateBugStatus(String newStatus, int bugID) throws BusinessException {

        Bug bug = bugDao.getBugByID(bugID);
        List<StatusType> allowedStatuses = StatusHelper.getStatusForUpdate(StatusType.valueOf(bug.getStatus()));

        if (!allowedStatuses.isEmpty() && allowedStatuses.contains(StatusType.valueOf(newStatus))) {
            return BugDTOEntityMapper.getBugDTO(this.bugDao.updateBugStatus(newStatus, bugID));
        } else {
            throw new BusinessException("msg-241", "Cannot modify " + bug.getStatus() + " into " + newStatus);
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
     * @throws {@link BusinessException}
     * @author Sebastian Maier
     */
    @Override
    public BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO, Integer createdID) throws BusinessException {
        if (wrapperDTO.getBug() == null || wrapperDTO.getAttachment() == null || wrapperDTO.getToken() == null) {
            throw new BusinessException("msg-500", "An Entity is empty.");
        } else {
            if (wrapperDTO.getBug().getASSIGNED_ID() == null) {
                throw new BusinessException("msg-504", "User is empty");
            } else {
                Bug bugToPersist = BugDTOEntityMapper.getBug(wrapperDTO.getBug());

                if (!BugValidator.validate(bugToPersist)) {
                    throw new BusinessException("msg-501", "Entity is not valid.");
                } else {

                    User createdUserToSet = userDao.findUser(createdID);
                    User assignedUserToSet = userDao.findUser(bugToPersist.getASSIGNED_ID().getID());
                    if (createdUserToSet == null || assignedUserToSet == null) {
                        throw new BusinessException("msg-501", "User does not exist");
                    } else {
                        // We set the users and change the Status from NEW to OPEN.
                        bugToPersist.setCREATED_ID(createdUserToSet);
                        bugToPersist.setASSIGNED_ID(assignedUserToSet);
                        bugToPersist.setStatus("OPEN");

                        Bug persistedBugWithID = bugDao.insert(bugToPersist);

                        if (persistedBugWithID.getID().equals(0) || persistedBugWithID.getID() == null) {
                            throw new BusinessException("msg-502", "Bug could not be added");
                        } else {
                            if (wrapperDTO.getAttachment().getAttContent() == null || wrapperDTO.getAttachment().getAttContent().length == 0) {
                                throw new BusinessException("msg-504", "Bug has to have an attachment");
                            } else {
                                Attachment attachmentToPersist = AttachmentDTOEntityMapper.getAttachment(wrapperDTO.getAttachment());
                                attachmentToPersist.setBugID(persistedBugWithID);

                                Attachment persistedAttachmentWithID = attachmentDao.insert(attachmentToPersist);
                                if (persistedAttachmentWithID.getID().equals(0) || persistedAttachmentWithID.getID() == null) {
                                    throw new BusinessException("msg-503", "Attachment could not be added");
                                } else
                                    return new BugAttachmentWrapperDTO(BugDTOEntityMapper.getBugDTO(persistedBugWithID),
                                            AttachmentDTOEntityMapper.getAttachmentDTO(persistedAttachmentWithID), wrapperDTO.getToken());
                            }
                        }
                    }
                }
            }
        }
    }
}
