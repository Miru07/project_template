package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

public class UserBugsDTO implements Serializable {

    private String firstName;
    private String lastName;
    private Integer nrBugs;

    public UserBugsDTO(String firstName, String lastName, Integer nrBugs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nrBugs = nrBugs;
    }

    @Override
    public String toString() {
        return "UserBugsDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nrBugs=" + nrBugs +
                '}';
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

    public Integer getNrBugs() {
        return nrBugs;
    }

    public void setNrBugs(Integer nrBugs) {
        this.nrBugs = nrBugs;
    }
}
