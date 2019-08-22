package ro.msg.edu.jbugs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginReceivedDTO implements Serializable {
    private String username;
    private String password;
}
