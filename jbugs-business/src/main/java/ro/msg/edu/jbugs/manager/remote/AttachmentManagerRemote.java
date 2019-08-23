package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.AttachmentDTO;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;

/**
 * Interface for Remote usage
 *
 * @author Sebastian Maier
 */
@Remote
public interface AttachmentManagerRemote {

    AttachmentDTO insertAttachment(@NotNull AttachmentDTO attachmentDTO);
}
