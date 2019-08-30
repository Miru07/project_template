package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserUpdateDTO implements Serializable {
    private UserDTO user;
    private String usernameUpdater;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(UserDTO user, String usernameUpdater) {
        this.user = user;
        this.usernameUpdater = usernameUpdater;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getUsernameUpdater() {
        return usernameUpdater;
    }

    public void setUsernameUpdater(String usernameUpdater) {
        this.usernameUpdater = usernameUpdater;
    }
}
