package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
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

    @Override
    public Integer updateBugStatus(String newStatus, int bugID) throws BusinessException {

        Bug bug = bugDao.getBugByID(bugID);

       if(bug.getStatus().equals("Open") && !(newStatus.equals("In progress") || newStatus.equals("Rejected")) ||
            bug.getStatus().equals("In progress") && !(newStatus.equals("Rejected") || newStatus.equals("Info needed") || newStatus.equals("Fixed")) ||
            bug.getStatus().equals("Rejected") && !newStatus.equals("Closed") ||
            bug.getStatus().equals("Fixed") && !(newStatus.equals("Open") || newStatus.equals("Closed")) ||
            bug.getStatus().equals("Info needed") && !(newStatus.equals("In progress")) ||
            bug.getStatus().equals("Closed")
            )

        {
            throw new BusinessException("msg-101", "Cannot change status from " + bug.getStatus() + " to " + newStatus);
        }
        return bugDao.updateBugStatus(newStatus, bugID);

    }

    @Override
    public List<BugDTO> getAllBugs(){
        List<Bug> bugList = bugDao.getAllBugs();

        return BugDTOEntityMapper.getBugDTOList(bugList);
    }
}
