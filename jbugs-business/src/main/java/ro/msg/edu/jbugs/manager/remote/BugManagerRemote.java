package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BugManagerRemote {

    List<BugDTO> findBugsCreatedBy(UserDTO userDTO);
    Integer updateBugStatus(String newStatus, int bugID) throws BusinessException;
    List <BugDTO> getAllBugs();
}
