package ro.msg.edu.jbugs.dto;

import java.io.Serializable;
import java.util.Set;

public class LoginResponseUserDTO implements Serializable {
    private String responseMessage;

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobileNumber;
    private Set<String> permissions; // list of types of permission

    public LoginResponseUserDTO() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.email = "";
        this.mobileNumber = "";

        this.permissions = null;
        this.responseMessage = "";
    }
    public LoginResponseUserDTO(UserDTO userDTO, Set<String> permissions){
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.mobileNumber = userDTO.getMobileNumber();

        this.permissions = permissions;
        this.responseMessage = "SUCCESS";
        // add permissions from roles, from user...
        // get roles of userID
        // get permissions of roleID
    }
    public LoginResponseUserDTO(Integer id, String firstName, String lastName, String username, String email, String mobileNumber, Set<String> permissions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.permissions = permissions;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
