package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BugManagerRemote {

    List<BugDTO> findBugsCreatedBy(UserDTO userDTO);
    BugDTO updateBugStatus(String newStatus, int bugId) throws BusinessException;
    List<BugDTO> getAllBugs();
}
