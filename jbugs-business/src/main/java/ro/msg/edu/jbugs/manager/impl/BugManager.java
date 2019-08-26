package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.interceptors.TimeInterceptors;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager class for CRUD actions on {@link Bug} & {@link BugDTO} objects.
 */
@Stateless
@Interceptors(TimeInterceptors.class)
public class BugManager implements BugManagerRemote {
    @EJB
    BugDao bugDao;
    @EJB
    UserDao userDao;
    @EJB
    AttachmentDao attachmentDao;

    @Override
    public List<BugDTO> findBugsCreatedBy(UserDTO userDTO){
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        List<Bug> bugs = bugDao.findBugCreatedBy(user);

        return bugs.stream().map(BugDTOEntityMapper::getBugDTO).collect(Collectors.toList());
    }

    @Override
    public Integer updateBugStatus(){

        return bugDao.updateBugStatus();
    }

    @Override
    public List<BugDTO> getAllBugs(){
        List<Bug> bugList = bugDao.getAllBugs();

        return BugDTOEntityMapper.getBugDTOList(bugList);
    }

    /**
     * The function breaks and maps the {@link BugAttachmentWrapperDTO} object to an {@link Bug} and an
     * {@link AttachmentDTO} object respectively.
     * The {@link Bug} object is persisted into the database. It comes with a set ID. This new object will be set to
     * the {@link Attachment} object and then persisted into the database.
     *
     * @param wrapperDTO is an {@link BugAttachmentWrapperDTO} object that contains
     *               the Bug details and Attachment details in a DTO format.
     * @return an {@link BugAttachmentWrapperDTO} object that has been persisted.
     * @author Sebastian Maier
     */
    @Override
    public BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO, Integer createdID) {
        Bug bugToInsert = BugDTOEntityMapper.getBug(wrapperDTO.getBug());
        Attachment attachmentToInsert = AttachmentDTOEntityMapper.getAttachment(wrapperDTO.getAttachment());
        User createdUserToAssign = userDao.findUser(createdID);
        System.out.println("USER TO CREATE: " + createdUserToAssign.toString());
        User assignedUserToAssign = userDao.findUser(bugToInsert.getASSIGNED_ID().getID());
        System.out.println("USER TO ASSIGN: " + assignedUserToAssign.toString());

        bugToInsert.setCREATED_ID(createdUserToAssign);
        bugToInsert.setASSIGNED_ID(assignedUserToAssign);

        Bug persistedBugWithID = bugDao.insert(bugToInsert);
        attachmentToInsert.setBugID(persistedBugWithID);

        Attachment persistedAttachmentWithID = attachmentDao.insert(attachmentToInsert);

        return new BugAttachmentWrapperDTO(
                BugDTOEntityMapper.getBugDTO(persistedBugWithID), AttachmentDTOEntityMapper.getAttachmentDTO(persistedAttachmentWithID),
                null
        );
    }
}
