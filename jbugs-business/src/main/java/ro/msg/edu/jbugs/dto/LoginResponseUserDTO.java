package ro.msg.edu.jbugs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseUserDTO implements Serializable {
    public static final String SUCCESS = "SUCCESS";

    private String messageCode;
    private String token;

    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobileNumber;
    private Set<String> permissions; // list of types of permission

    public LoginResponseUserDTO(UserDTO userDTO, Set<String> permissions){
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.mobileNumber = userDTO.getMobileNumber();

        this.permissions = permissions;
        this.messageCode = LoginResponseUserDTO.SUCCESS;
        this.token = "";
    }
}
