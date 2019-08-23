package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.AttachmentDTO;

import javax.ejb.Remote;

/**
 * Interface for Remote usage
 *
 * @author Sebastian Maier
 */
@Remote
public interface AttachmentManagerRemote {

    AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO);
}
