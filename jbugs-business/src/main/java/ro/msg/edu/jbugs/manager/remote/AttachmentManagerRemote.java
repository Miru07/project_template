package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.AttachmentDTO;

import javax.ejb.Remote;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface AttachmentManagerRemote {

    AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO);
}
