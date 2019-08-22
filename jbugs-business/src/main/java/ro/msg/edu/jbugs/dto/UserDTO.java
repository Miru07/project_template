package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int counter;
    private String email;
    private String mobileNumber;
    private Integer status;

    public UserDTO(int id, String firstName, String lastName, String username, String password, int counter, String email, String mobileNumber, Integer status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.counter = counter;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
    }

    public UserDTO(Integer counter, String firstName, String lastName, String mobileNumber, String email,
                   String username, String password, Integer status) {

        this.counter = counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public UserDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", counter=" + counter +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", status=" + status +
                '}';
    }

    public String prettyPrint(){

        return id + ". " + username;
    }
}
