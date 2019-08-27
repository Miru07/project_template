package ro.msg.edu.jbugs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.msg.edu.jbugs.entity.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Integer counter, String firstName, String lastName, String mobileNumber, String email,
                   String username, Integer status) {
        this.counter = counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.username = username;
        this.password = "";
        this.status = status;
    }

    public UserDTO(Integer counter, String firstName, String lastName, String mobileNumber, String email,
                   String username, String password, Integer status) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "counter=" + counter +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", id=" + id +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                ", username='" + username + '\'' +
                '}';
    }

    public String prettyPrint(){
        return id + ". " + username;
    }
}
