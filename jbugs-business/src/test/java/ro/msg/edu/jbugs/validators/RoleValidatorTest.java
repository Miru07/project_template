package ro.msg.edu.jbugs.validators;

import org.junit.Test;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.PermissionDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleValidatorTest {

    @Test
    public void validatePermissionsInsertionSuccess() throws BusinessException {
        PermissionDTO[] permissions = new PermissionDTO[1];
        Permission permission = new Permission(1, "", PermissionType.PERMISSION_MANAGEMENT);
        permissions[0] = PermissionDTOEntityMapper.getDTOFromPermission(permission);

        RoleValidator.validatePermissionsInsertion(new PermissionsInsertDTO(1, permissions));
    }

    @Test(expected = BusinessException.class)
    public void validatePermissionsInsertionFailForAdmin() throws BusinessException {
        PermissionDTO[] permissions = new PermissionDTO[1];
        Permission permission = new Permission(1, "", PermissionType.USER_MANAGEMENT);
        permissions[0] = PermissionDTOEntityMapper.getDTOFromPermission(permission);

        RoleValidator.validatePermissionsInsertion(new PermissionsInsertDTO(1, permissions));
    }
}