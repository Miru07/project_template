package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager class for CRUD actions on {@link Attachment} & {@link AttachmentDTO} objects.
 *
 * @author Sebastian Maier
 */
@Stateless
public class AttachmentManager implements AttachmentManagerRemote {
    @EJB
    AttachmentDao attachmentDao;

    /**
     * @param attachmentDTO is an {@link AttachmentDTO} object that will be persisted
     *                      in the database.
     * @return an {@link AttachmentDTO} with a persisted ID.
     */
    @Override
    public AttachmentDTO insertAttachment(@NotNull AttachmentDTO attachmentDTO) {
        Attachment attachmentToAdd = AttachmentDTOEntityMapper.getAttachment(attachmentDTO);

        Attachment attachmentWithFlushedID = attachmentDao.insert(attachmentToAdd);

        return AttachmentDTOEntityMapper.getAttachmentDTO(attachmentWithFlushedID);
    }

    /**
     * @return a list of {@link AttachmentDTO} from the database
     */
    @Override
    public List<AttachmentDTO> getAllAtt() {

        List<AttachmentDTO> attachmentDTOList = new ArrayList<>();
        List<Attachment> attachmentList = attachmentDao.getAllAtt();

        for(Attachment a: attachmentList){
            attachmentDTOList.add(AttachmentDTOEntityMapper.getAttachmentDTO(a));
        }

        return attachmentDTOList;
    }

    /**
     * @param attachmentID the id of the {@link Attachment} that must be removed from the database
     * @return the attachment that was removed
     */
    @Override
    public AttachmentDTO deleteAttachment(int attachmentID) {
        return AttachmentDTOEntityMapper.getAttachmentDTO(this.attachmentDao.delete(attachmentID));
    }


}
