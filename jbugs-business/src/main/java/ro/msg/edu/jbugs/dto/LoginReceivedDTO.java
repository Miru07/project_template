package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

public class LoginReceivedDTO implements Serializable {
    private String username;
    private String hashedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}