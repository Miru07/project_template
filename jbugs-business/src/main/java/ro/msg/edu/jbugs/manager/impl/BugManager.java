package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.interceptors.TimeInterceptors;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.validation.constraints.NotNull;
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
     * The function maps the {@link BugDTO} object to an {@link Bug} object.
     * The latter object is then persisted inside the database.
     *
     * @param bugDTO is an {@link BugDTO} object that contains
     *               the Bug details in a DTO format.
     * @return an {@link BugDTO} object that has been persisted and contains and ID.
     * @author Sebastian Maier
     */
    @Override
    public BugDTO insertBug(@NotNull BugDTO bugDTO) {
        Bug bugToAdd = BugDTOEntityMapper.getBug(bugDTO);
        Bug bugWithFlushedID = bugDao.insert(bugToAdd);

        return BugDTOEntityMapper.getBugDTO(bugWithFlushedID);
    }
}
