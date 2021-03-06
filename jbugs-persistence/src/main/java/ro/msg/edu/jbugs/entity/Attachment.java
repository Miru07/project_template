package ro.msg.edu.jbugs.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name="attachments")
@NamedQueries({
        @NamedQuery(name = Attachment.FIND_ALL_ATT, query = "SELECT a FROM Attachment a")

})
public class Attachment implements Serializable {

    public static final String FIND_ALL_ATT = "findAllAttachments";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer ID;

    @Lob
    @Column(name="attContent")
    private byte[] attContent;

    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="id_bug",referencedColumnName = "ID")
    private Bug bugID;

    public Attachment() {
    }

    public Attachment(Integer id, byte[] attContent, Bug bugID) {
        this.ID = id;
        this.attContent = attContent;
        this.bugID = bugID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Bug getBugID() {
        return bugID;
    }

    public void setBugID(Bug id_bug) {
        this.bugID = id_bug;
    }

    public byte[] getAttContent() {
        return attContent;
    }

    public void setAttContent(byte[] attContent) {
        this.attContent = attContent;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "ID=" + ID +
                ", attContent=" + Arrays.toString(attContent) +
                ", bugID=" + bugID +
                '}';
    }
}
