package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.AttachmentDTO;

import java.util.List;

public interface AttachmentManagerRemote {

    List<AttachmentDTO> getAttDTOFromBug(Integer bugID);
}
