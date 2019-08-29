package ro.msg.edu.jbugs.dto;

import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.types.PermissionType;

import java.io.Serializable;

/**
 * The class maps a {@link Permission} object.
 *
 * @author Mara Corina
 */
public class PermissionDTO implements Serializable {
    private Integer id;
    private PermissionType type;

    public PermissionDTO() {
    }

    public PermissionDTO(Integer id, PermissionType type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }
}
