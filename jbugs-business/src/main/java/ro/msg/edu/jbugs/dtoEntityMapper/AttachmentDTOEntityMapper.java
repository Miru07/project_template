package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.entity.Attachment;

/**
 * Entity Mapper class for {@link Attachment} & {@link AttachmentDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Sebastian Maier
 */
public class AttachmentDTOEntityMapper {

    private AttachmentDTOEntityMapper() {
    }

    public static Attachment getAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();

        attachment.setBugID(BugDTOEntityMapper.getBugWithUsersWithoutRoles(attachmentDTO.getBugID()));
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
