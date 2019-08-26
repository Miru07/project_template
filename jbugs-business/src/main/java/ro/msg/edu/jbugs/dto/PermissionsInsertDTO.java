package ro.msg.edu.jbugs.dto;

import java.io.Serializable;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
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
