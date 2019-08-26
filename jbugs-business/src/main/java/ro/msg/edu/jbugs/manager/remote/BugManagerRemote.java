package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface BugManagerRemote {

    List<BugDTO> findBugsCreatedBy(UserDTO userDTO);
    BugDTO updateBugStatus(String newStatus, int bugId);
    List<BugDTO> getAllBugs();
}
