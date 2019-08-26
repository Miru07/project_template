package ro.msg.edu.jbugs.dto;


import java.io.Serializable;
import java.sql.Date;

/**
 * DTO class for a {@link BugDTO} class without {@link UserDTO} attributes
 *
 * @author Sebastian Maier
 */
public class BugWithoutUsersDTO implements Serializable {
    private Integer ID;
    private String title;
    private String description;
    private String version;
    private Date targetDate;
    private String status;
    private String fixedVersion;
    private String severity;
    private Integer CREATED_ID;
    private Integer ASSIGNED_ID;

    public BugWithoutUsersDTO() {
    }

    public BugWithoutUsersDTO(Integer ID, String title, String description, String version, Date targetDate, String status, String fixedVersion, String severity, Integer CREATED_ID, Integer ASSIGNED_ID) {
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

    public Integer getCREATED_ID() {
        return CREATED_ID;
    }

    public void setCREATED_ID(Integer CREATED_ID) {
        this.CREATED_ID = CREATED_ID;
    }

    public Integer getASSIGNED_ID() {
        return ASSIGNED_ID;
    }

    public void setASSIGNED_ID(Integer ASSIGNED_ID) {
        this.ASSIGNED_ID = ASSIGNED_ID;
    }
}