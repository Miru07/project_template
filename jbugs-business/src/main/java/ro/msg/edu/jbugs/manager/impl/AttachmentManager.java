package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class AttachmentManager implements AttachmentManagerRemote {
    @EJB
    AttachmentDao attachmentDao;

    @Override
    public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachmentToAdd = AttachmentDTOEntityMapper.getAttachment(attachmentDTO);

        Attachment attachmentWithFlushedID = attachmentDao.insert(attachmentToAdd);

        return AttachmentDTOEntityMapper.getAttachmentDTO(attachmentWithFlushedID);
    }
}
