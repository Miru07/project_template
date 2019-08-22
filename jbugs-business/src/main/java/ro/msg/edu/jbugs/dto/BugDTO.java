package ro.msg.edu.jbugs.dto;

import java.io.Serializable;
import java.sql.Date;

public class BugDTO implements Serializable {
    private Integer ID;
    private String title;
    private String description;
    private String version;
    private Date targetDate;
    private String status;
    private String fixedVersion;
    private String severity;
    private UserDTO CREATED_ID;
    private UserDTO ASSIGNED_ID;

    public BugDTO(){

    }

    @Override
    public String toString() {
        return "BugDTO{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", status='" + status + '\'' +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity='" + severity + '\'' +
                ", CREATED_ID=" + CREATED_ID +
                ", ASSIGNED_ID=" + ASSIGNED_ID +
                '}';
    }

    public BugDTO(Integer ID, String title, String description, String version, Date targetDate, String status, String fixedVersion, String severity, UserDTO CREATED_ID, UserDTO ASSIGNED_ID) {
        this.ID = ID;
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

    public BugDTO(String title, String description, String version, Date targetDate, String status, String fixedVersion, String severity, UserDTO CREATED_ID, UserDTO ASSIGNED_ID) {
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

    public Date getTargetDate() {
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

    public UserDTO getCREATED_ID() {
        return CREATED_ID;
    }

    public void setCREATED_ID(UserDTO CREATED_ID) {
        this.CREATED_ID = CREATED_ID;
    }

    public UserDTO getASSIGNED_ID() {
        return ASSIGNED_ID;
    }

    public void setASSIGNED_ID(UserDTO ASSIGNED_ID) {
        this.ASSIGNED_ID = ASSIGNED_ID;
    }
}
