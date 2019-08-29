package ro.msg.edu.jbugs.dto;

import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.User;

import java.io.Serializable;
import java.sql.Date;

/**
 * The class maps a {@link Notification} object.
 *
 * @author Mara Corina
 */
public class NotificationDTO implements Serializable {

    private Integer ID;
    private Date date;
    private String message;
    private String type;
    private String url;
    private User user_id;

    public NotificationDTO(Date date, String message, String type, String url, User user_id) {
        this.date = date;
        this.message = message;
        this.type = type;
        this.url = url;
        this.user_id = user_id;
    }

    public NotificationDTO(){

    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
}
