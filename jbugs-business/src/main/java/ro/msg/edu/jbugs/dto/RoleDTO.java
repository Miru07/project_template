package ro.msg.edu.jbugs.dto;

import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.types.RoleType;

import java.io.Serializable;

/**
 * The class maps a {@link Role} object.
 *
 * @author Mara Corina
 */
public class RoleDTO implements Serializable {
    private RoleType type;

    public RoleDTO() {
    }

    public RoleDTO(RoleType type) {
        this.type = type;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "type=" + type +
                '}';
    }
}
