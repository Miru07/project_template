package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

/**
 * Manager class for CRUD actions on {@link Role}.
 *
 * @author Mara Corina
 */
public class RoleValidator {

    public static void validatePermissionsInsertion(PermissionsInsertDTO permissionDTOS) throws BusinessException {
        validatePermissionsForAdmin(permissionDTOS);
    }


    private static void validatePermissionsForAdmin(PermissionsInsertDTO permissionsInsertDTOS) throws BusinessException {
        if (permissionsInsertDTOS.getRoleId() == 1) {
            boolean isValid = false;
            for (PermissionDTO permissionDTO : permissionsInsertDTOS.getPermissions()) {
                if (permissionDTO.getType().equals(PermissionType.PERMISSION_MANAGEMENT))
                    isValid = true;
            }
            if (!isValid)
                throw new BusinessException("msg2_302", "Invalid permissions for Admin!");
        }
    }
}
