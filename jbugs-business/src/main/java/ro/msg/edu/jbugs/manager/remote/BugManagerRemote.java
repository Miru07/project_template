package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;

import javax.ejb.Remote;
import java.util.List;

/**
 * Interface for Remote usage.
 *
 * @author Sebastian Maier
 */
@Remote
public interface BugManagerRemote {

    List<BugDTO> findBugsCreatedBy(UserDTO userDTO);

    Integer updateBugStatus();

    List<BugDTO> getAllBugs();

    BugDTO insertBug(BugDTO bugDTO);
}
