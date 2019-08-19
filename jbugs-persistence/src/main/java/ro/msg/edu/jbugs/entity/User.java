package ro.msg.edu.jbugs.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries({

        @NamedQuery(name = User.FIND_ALL_USERS, query = "select u from User u"),
        @NamedQuery(name = User.CHECK_USERNAME_UNIQUE, query = "select count(u) from User u where u.username=:username"),
        @NamedQuery(name = User.QUERY_SELECT_BY_USERNAME_AND_PASSWWORD, query = "select u from User u " +
                                                                                "where u.username=:username " +
                                                                                "and u.password=:password"),
        @NamedQuery(name = User.GET_USER_BY_USERNAME, query = "select u from User u where u.username=:username")
})

public class User implements Serializable {
    public static final String FIND_ALL_USERS = "findAllUsers";
    public static final String CHECK_USERNAME_UNIQUE = "checkUsernameUnique";
    public static final String QUERY_SELECT_BY_USERNAME_AND_PASSWWORD = "User.querySelectUsernameAndPassword";
    public static final String GET_USER_BY_USERNAME = "User.getUserByUsername";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="ID")
    private Integer ID;
    @Column (name="counter")
    private Integer counter;
    @Column (name="first_name")
    private String firstName;
    @Column (name="last_name")
    private String lastName;
    @Column (name="mobile_number")
    private String mobileNumber;
    @Column (name="email")
    private String email;
    @Column (name="username")
    private String username;
    @Column (name="password")
    private  String password;
    @Column (name="status")
    private Integer status;

    @OneToMany
    private Set<Bug> createdBugs = new HashSet<>();
    @OneToMany
    private Set<Bug> assignedBugs = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH)
    private Set<Notification> notifications = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public User (){
    }

    public User(Integer counter, String firstName, String lastName, String mobileNumber, String email, String username,
                String password, Integer status) {
        this.counter = counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Bug> getCreatedBugs() {
        return createdBugs;
    }

    public void setCreatedBugs(Set<Bug> createdBugs) {
        this.createdBugs = createdBugs;
    }

    public Set<Bug> getAssignedBugs() {
        return assignedBugs;
    }

    public void setAssignedBugs(Set<Bug> assignedBugs) {
        this.assignedBugs = assignedBugs;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "{ counter=" + counter +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
