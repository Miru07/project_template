package ro.msg.edu.jbugs.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="comments")
public class Comment implements Serializable {

    public static final String DELETE_COMMENTS_OLDER_THAN_1YEAR = "deleteCommnentsOlderThan1Year";

    private String text;
    private Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "ID")
    private User userID;

    @ManyToOne
    @JoinColumn(name="bug_id", referencedColumnName = "ID")
    private Bug bugID;

    public Comment() {
    }

    public Comment(String text, Date date, User user_id, Bug bug_id) {
        this.text = text;
        this.date = date;
        this.userID = user_id;
        this.bugID = bug_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User user_id) {
        this.userID = user_id;
    }

    public Bug getBugID() {
        return bugID;
    }

    public void setBugID(Bug bug_id) {
        this.bugID = bug_id;
    }
}
