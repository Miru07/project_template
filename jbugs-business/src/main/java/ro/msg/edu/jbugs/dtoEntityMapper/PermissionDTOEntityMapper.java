package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.entity.Permission;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity Mapper class for {@link Permission} & {@link PermissionDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Mara Corina
 */
public class PermissionDTOEntityMapper {

    private PermissionDTOEntityMapper() {

    }


    public static PermissionDTO getDTOFromPermission(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();

        if (permission != null) {
            permissionDTO.setId(permission.getID());
            permissionDTO.setType(permission.getType());
        }

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
