package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.StatusType;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.helpers.StatusHelper;
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

    @Override
    public List<BugDTO> findBugsCreatedBy(UserDTO userDTO){
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        List<Bug> bugs = bugDao.findBugCreatedBy(user);

        return bugs.stream().map(BugDTOEntityMapper::getBugDTO).collect(Collectors.toList());
    }
    //empty list for closed

    @Override
    public BugDTO updateBugStatus(String newStatus, int bugID) throws BusinessException {

        Bug bug = bugDao.getBugByID(bugID);
        List<StatusType> allowedStatuses = StatusHelper.getStatusForUpdate(StatusType.valueOf(bug.getStatus()));

       if(allowedStatuses.isEmpty() && allowedStatuses.contains(StatusType.valueOf(newStatus)))
       {
           return BugDTOEntityMapper.getBugDTO(this.bugDao.updateBugStatus(newStatus, bugID));
       }
       else {
           throw new BusinessException("msg-241", "Cannot modify " + bug.getStatus() + " into " + newStatus);
       }

    }

    @Override
    public List<BugDTO> getAllBugs(){
        List<Bug> bugList = bugDao.getAllBugs();

        return BugDTOEntityMapper.getBugDTOList(bugList);
    }

}
