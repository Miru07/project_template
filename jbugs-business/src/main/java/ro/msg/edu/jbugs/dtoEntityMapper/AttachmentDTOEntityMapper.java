package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.entity.Attachment;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class AttachmentDTOEntityMapper {

    private AttachmentDTOEntityMapper() {
    }

    public static Attachment getAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();

        attachment.setBugID(BugDTOEntityMapper.getBug(attachmentDTO.getBugID()));
        attachment.setID(attachmentDTO.getID());
        attachment.setAttContent(attachmentDTO.getAttContent());

        return attachment;
    }

    public static AttachmentDTO getAttachmentDTO(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();

        attachmentDTO.setBugID(BugDTOEntityMapper.getBugDTO(attachment.getBugID()));
        attachmentDTO.setID(attachment.getID());
        attachmentDTO.setAttContent(attachment.getAttContent());

        return attachmentDTO;
    }
}
