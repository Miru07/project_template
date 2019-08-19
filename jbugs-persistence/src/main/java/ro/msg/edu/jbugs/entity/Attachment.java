package ro.msg.edu.jbugs.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="attachments")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer ID;

    @Column(name="attContent")
    private String attContent;

    @ManyToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="id_bug",referencedColumnName = "ID")
    private Bug bugID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getAttContent() {
        return attContent;
    }

    public void setAttContent(String attContent) {
        this.attContent = attContent;
    }

    public Bug getBugID() {
        return bugID;
    }

    public void setBugID(Bug id_bug) {
        this.bugID = id_bug;
    }
}
