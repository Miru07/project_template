package ro.msg.edu.jbugs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BugViewDTO implements Serializable {

    List<BugDTO> bugDTOList = new ArrayList<>();
    List<UserDTO> userDTOList = new ArrayList<>();

    public BugViewDTO(List<BugDTO> bugDTOList, List<UserDTO> userDTOList) {
        this.bugDTOList = bugDTOList;
        this.userDTOList = userDTOList;
    }

    public List<BugDTO> getBugDTOList() {
        return bugDTOList;
    }

    public void setBugDTOList(List<BugDTO> bugDTOList) {
        this.bugDTOList = bugDTOList;
    }

    public List<UserDTO> getUserDTOList() {
        return userDTOList;
    }

    public void setUserDTOList(List<UserDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }
}
