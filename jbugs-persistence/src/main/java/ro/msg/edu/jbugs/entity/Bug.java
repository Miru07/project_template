package ro.msg.edu.jbugs.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "bugs")
@NamedQueries({
        @NamedQuery(name = Bug.FIND_BUGS_CREATED_ID, query = "SELECT b FROM Bug b WHERE b.CREATED_ID.ID = :var_user_id"),
        @NamedQuery(name = Bug.FIND_ALL_BUGS, query = "SELECT b FROM Bug b")
})
public class Bug implements Serializable {

    public static final String FIND_BUGS_CREATED_ID = "findBugsCreatedByUser";
    public static final String FIND_ALL_BUGS = "findAllBugs";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "version")
    private String version;
    @Column(name = "targetDate")
    private java.sql.Date targetDate;
    @Column(name = "status")
    private String status;
    @Column(name = "fixedVersion")
    private String fixedVersion;
    @Column(name = "severity")
    private String severity;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "CREATED_ID")
    private User CREATED_ID;
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "ASSIGNED_ID")
    private User ASSIGNED_ID;

    @OneToMany(mappedBy = "bugID")
    private List<Comment> comments;

    @OneToMany(mappedBy = "bugID")
    private List<Attachment> attachments;


    public Bug(){
    }

    public Bug(Integer id, String title, String description, String version, Date targetDate, String status, String fixedVersion, String severity, User CREATED_ID, User ASSIGNED_ID) {
        this.ID = id;
        this.title = title;
        this.description = description;
        this.version = version;
        this.targetDate = targetDate;
        this.status = status;
        this.fixedVersion = fixedVersion;
        this.severity = severity;
        this.CREATED_ID = CREATED_ID;
        this.ASSIGNED_ID = ASSIGNED_ID;
    }

    public User getCREATED_ID() {
        return CREATED_ID;
    }

    public void setCREATED_ID(User CREATED_ID) {
        this.CREATED_ID = CREATED_ID;
    }

    public User getASSIGNED_ID() {
        return ASSIGNED_ID;
    }

    public void setASSIGNED_ID(User ASSIGNED_ID) {
        this.ASSIGNED_ID = ASSIGNED_ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public java.sql.Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate=" + targetDate +
                ", status='" + status + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity='" + severity + '\'' +
                ", CREATED_ID=" + CREATED_ID +
                ", ASSIGNED_ID=" + ASSIGNED_ID +
                '}';
    }
}