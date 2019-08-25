package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;

import javax.ejb.EJB;
import java.util.List;

public class AttachmentManager implements AttachmentManagerRemote {

    @EJB
    AttachmentDao attachmentDao;

    @Override
    public List<AttachmentDTO> getAttDTOFromBug(Integer bugID) {
        List<Attachment> attachments = attachmentDao.getAttachmentsFromBugID(bugID);
        return AttachmentDTOEntityMapper.getAttachmentDTOList(attachments);
    }
}
