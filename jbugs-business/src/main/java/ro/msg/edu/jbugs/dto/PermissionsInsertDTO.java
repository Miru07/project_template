package ro.msg.edu.jbugs.dto;

import ro.msg.edu.jbugs.entity.Role;

import java.io.Serializable;

/**
 * The class maps a {@link Role} object id and an array of {@link PermissionDTO} objects
 *
 * @author Mara Corina
 */
public class PermissionsInsertDTO implements Serializable {
    private Integer roleId;
    private PermissionDTO[] permissions;

    public PermissionsInsertDTO() {
    }

    public PermissionsInsertDTO(Integer roleId, PermissionDTO[] permissions) {
        this.roleId = roleId;
        this.permissions = permissions;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public PermissionDTO[] getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionDTO[] permissions) {
        this.permissions = permissions;
    }
}
