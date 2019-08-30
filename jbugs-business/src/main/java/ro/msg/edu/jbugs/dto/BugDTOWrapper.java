package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * DTO wrapper class that contains a {@link BugDTO} and token.
 *
 * @author Sebastian Maier
 */
public class BugDTOWrapper implements Serializable {
    private BugDTO bugDTO;
    private String token;

    public BugDTOWrapper() {
    }

    public BugDTOWrapper(BugDTO bugDTO, String token) {
        this.bugDTO = bugDTO;
        this.token = token;
    }

    public BugDTO getBugDTO() {
        return bugDTO;
    }

    public void setBugDTO(BugDTO bugDTO) {
        this.bugDTO = bugDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
