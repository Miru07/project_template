package ro.msg.edu.jbugs.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="notifications")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="ID")
    private Integer ID;

    private Date date;
    private String message;
    private String type;
    private String url;

    @ManyToOne(cascade = CascadeType.MERGE)
//    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "ID")
    private User user;

    public Notification(Date date, String message, String type, String url, User user_id) {
        this.date = date;
        this.message = message;
        this.type = type;
        this.url = url;
        this.user = user_id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user_id) {
        this.user = user_id;
    }
}
