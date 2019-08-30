
package ro.msg.edu.jbugs.dto;


import ro.msg.edu.jbugs.entity.types.NotificationType;

import java.io.Serializable;
import java.sql.Date;

public class NotificationDTO implements Serializable {

    private Integer ID;
    private Date date;
    private String message;
    private NotificationType type;
    private String url;
    private UserDTO user;

    public NotificationDTO(Date date, String message, NotificationType type, String url, UserDTO user_id) {
        this.date = date;
        this.message = message;
        this.type = type;
        this.url = url;
        this.user = user_id;
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

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
