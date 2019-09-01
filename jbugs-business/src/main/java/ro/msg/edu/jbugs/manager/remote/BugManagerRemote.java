package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.BugViewDTO;
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

    BugDTO closeBug(int bugID) throws BusinessException;

    BugViewDTO getBugViewDTO();

    BugDTO getBugById(Integer id);

    BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO wrapperDTO, Integer createdID) throws BusinessException;

    BugAttachmentWrapperDTO updateBug(Integer requestUserID, Integer bugID, BugDTO bugToUpdate,
                                      AttachmentDTO attachmentDTO, String token) throws BusinessException;
}
