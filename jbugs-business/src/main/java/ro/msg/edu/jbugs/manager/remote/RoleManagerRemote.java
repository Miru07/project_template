package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Remote
public interface RoleManagerRemote {

    Set<PermissionDTO> getRolePermissions(int id) throws BusinessException;

    void setRolePermissions(int roleId, PermissionDTO[] rolePermissions) throws BusinessException;

}
