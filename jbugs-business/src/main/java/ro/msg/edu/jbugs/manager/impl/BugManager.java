package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dao.BugDao;
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

@Stateless
@Interceptors(TimeInterceptors.class)
public class BugManager implements BugManagerRemote {
    @EJB
    BugDao bugDao;
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

    @Override
    public BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO) {
        Bug bugToAdd = BugDTOEntityMapper.getBug(wrapperDTO.getBug());
        Bug bugFlushedWithID = bugDao.insert(bugToAdd);

        Attachment attachmentToAdd = AttachmentDTOEntityMapper.getAttachment(wrapperDTO.getAttachment());
        attachmentToAdd.setBugID(bugFlushedWithID);

        Attachment attachmentFlushedWithID = attachmentDao.insert(attachmentToAdd);

        return new BugAttachmentWrapperDTO(
                BugDTOEntityMapper.getBugDTO(bugFlushedWithID),
                AttachmentDTOEntityMapper.getAttachmentDTO(attachmentFlushedWithID)
        );
    }
}
