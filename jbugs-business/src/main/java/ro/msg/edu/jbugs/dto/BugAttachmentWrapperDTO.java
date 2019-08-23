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
    private String token;

    public BugAttachmentWrapperDTO() {
    }

    public BugAttachmentWrapperDTO(BugDTO bug, AttachmentDTO attachment, String token) {
        this.bug = bug;
        this.attachment = attachment;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BugAttachmentWrapperDTO{" +
                "bug=" + bug +
                ", attachment=" + attachment +
                '}';
    }


}
