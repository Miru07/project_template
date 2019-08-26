package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleValidator {

    public static void validatePermissionsInsertion(PermissionsInsertDTO permissionDTOS) throws BusinessException {
        if (permissionDTOS.getRoleId() == 1 && !validPermissionsForAdmin(permissionDTOS.getPermissions()))
            throw new BusinessException("msg2_302", "Invalis permissions for Admin!");
        if (!validPermissions(permissionDTOS.getPermissions()))
            throw new BusinessException("msg2_303", "Invalis permissions!");
    }


    static boolean validPermissions(PermissionDTO[] permissionDTOS) {
        for (PermissionDTO permissionDTO : permissionDTOS) {
            if (!permissionDTO.getType().equals("PERMISSION_MANAGEMENT")
                    && !permissionDTO.getType().equals("USER_MANAGEMENT")
                    && !permissionDTO.getType().equals("BUG_MANAGEMENT")
                    && !permissionDTO.getType().equals("BUG_CLOSE")
                    && !permissionDTO.getType().equals("BUG_EXPORT_PDF"))
                return false;
        }
        return true;
    }

    static boolean validPermissionsForAdmin(PermissionDTO[] permissionDTOS) {
        for (PermissionDTO permissionDTO : permissionDTOS) {
            if (permissionDTO.getType().equals("PERMISSION_MANAGEMENT"))
                return true;
        }
        return false;
    }
}
