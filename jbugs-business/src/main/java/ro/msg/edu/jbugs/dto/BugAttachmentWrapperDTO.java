package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugAttachmentWrapperDTO implements Serializable {
    private BugDTO bug;
    private AttachmentDTO attachment;

    public BugAttachmentWrapperDTO() {
    }

    public BugAttachmentWrapperDTO(BugDTO bug, AttachmentDTO attachment) {
        this.bug = bug;
        this.attachment = attachment;
    }

    public BugDTO getBug() {
        return bug;
    }

    public void setBug(BugDTO bug) {
        this.bug = bug;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "BugAttachmentWrapperDTO{" +
                "bug=" + bug +
                ", attachment=" + attachment +
                '}';
    }
}
