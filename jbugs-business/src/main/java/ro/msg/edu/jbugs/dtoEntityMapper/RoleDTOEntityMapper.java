package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.entity.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class RoleDTOEntityMapper {


    private RoleDTOEntityMapper(){

    }


    public static RoleDTO getDTOFromRole(Role role){

        RoleDTO roleDTO = new RoleDTO(role.getType());
        return roleDTO;
    }

    public static Set<RoleDTO> getRoleDTOListFromRoleList(Set<Role> roles){

        Set<RoleDTO> roleDTOList = new HashSet<>();

        for(Role role : roles){

            roleDTOList.add(getDTOFromRole(role));
        }

        return roleDTOList;
    }
}
