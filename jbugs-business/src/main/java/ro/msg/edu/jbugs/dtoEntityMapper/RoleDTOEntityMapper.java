package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.entity.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity Mapper class for {@link Role} & {@link RoleDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Mara Corina
 */
public class RoleDTOEntityMapper {


    private RoleDTOEntityMapper(){

    }


    public static RoleDTO getDTOFromRole(Role role){
        RoleDTO roleDTO = new RoleDTO();
        if (role != null)
            roleDTO.setType(role.getType());
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
