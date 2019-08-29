package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

/**
 * Interface for Remote usage.
 *
 * @author Sebastian Maier
 */
@Remote
public interface BugManagerRemote {

    //List<BugDTO> findBugsCreatedBy(UserDTO userDTO);

    List<BugDTO> getAllBugs();

    BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO, Integer createdID) throws BusinessException;

    BugDTO updateBug(Integer requestUserID, Integer bugID, BugDTO bugToUpdate) throws BusinessException;
}
