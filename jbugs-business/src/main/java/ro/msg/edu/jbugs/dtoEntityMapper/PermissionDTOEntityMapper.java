package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.entity.Permission;

import java.util.HashSet;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class PermissionDTOEntityMapper {

    private PermissionDTOEntityMapper() {

    }


    public static PermissionDTO getDTOFromPermission(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO(permission.getID(), permission.getType());
        return permissionDTO;
    }

    public static Set<PermissionDTO> getPermissionDTOListFromPermissionList(Set<Permission> permissions) {

        Set<PermissionDTO> permissionDTOList = new HashSet<>();

        for (Permission permission : permissions) {

            permissionDTOList.add(getDTOFromPermission(permission));
        }

        return permissionDTOList;
    }
}
