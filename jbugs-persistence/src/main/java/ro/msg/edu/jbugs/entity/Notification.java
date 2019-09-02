package ro.msg.edu.jbugs.entity;


import ro.msg.edu.jbugs.entity.types.NotificationType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="notifications")
@NamedQuery(name = Notification.NOTIFICATION_DELETE_OLDER_THAN_ONE_MONTH, query = "DELETE FROM Notification n WHERE n.date < :date")
public class Notification implements Serializable {

    public static final String NOTIFICATION_DELETE_OLDER_THAN_ONE_MONTH = "Notification.deleteNotificationsOlderThanOneMonth";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="ID")
    private Integer ID;

    private Date date;
    private String message;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String url;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id",referencedColumnName = "ID")
    private User user;

    public Notification(Integer id, Date date, String message, NotificationType type, String url, User user) {
        this.ID = id;
        this.date = date;
        this.message = message;
        this.type = type;
        this.url = url;
        this.user = user;
    }

    public Notification(Date date, String message, NotificationType type, String url, User user) {
        this.date = date;
        this.message = message;
        this.type = type;
        this.url = url;
        this.user = user;
    }

    public Notification() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user_id) {
        this.user = user_id;
    }
}
