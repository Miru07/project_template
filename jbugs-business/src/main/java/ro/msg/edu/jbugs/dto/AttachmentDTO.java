package ro.msg.edu.jbugs.dto;

import java.io.Serializable;
import java.util.Arrays;

public class AttachmentDTO implements Serializable {
    private Integer ID;
    private byte[] attContent;
    private BugDTO bugID;

    public AttachmentDTO() {
    }

    public AttachmentDTO(Integer ID, byte[] attContent, BugDTO bugID) {
        this.ID = ID;
        this.attContent = attContent;
        this.bugID = bugID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public byte[] getAttContent() {
        return attContent;
    }

    public void setAttContent(byte[] attContent) {
        this.attContent = attContent;
    }

    public BugDTO getBugID() {
        return bugID;
    }

    public void setBugID(BugDTO bugID) {
        this.bugID = bugID;
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
                "ID=" + ID +
                ", attContent=" + Arrays.toString(attContent) +
                ", bugID=" + bugID +
                '}';
    }
}
