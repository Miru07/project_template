package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.Set;

/**
 * Interface for Remote usage
 *
 * @author Mara Corina
 */
@Remote
public interface RoleManagerRemote {

    Set<PermissionDTO> getRolePermissions(int id) throws BusinessException;

    void setRolePermissions(PermissionsInsertDTO permissionsInsertDTO) throws BusinessException;
}
