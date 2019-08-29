package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.PermissionDao;
import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.PermissionDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.RoleManagerRemote;
import ro.msg.edu.jbugs.validators.RoleValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashSet;
import java.util.Set;

/**
 * Manager class for CRUD actions on {@link Role} & {@link Permission} objects.
 *
 * @author Mara Corina
 */
@Stateless
public class RoleManager implements RoleManagerRemote {

    @EJB
    private RoleDao roleDao;

    @EJB
    private PermissionDao permissionDao;

    @EJB
    private NotificationManagerRemote notificationManager;

    //private Logger logger = Logger.getLogger(RoleManager.class.getName());


    /**
     * Gets the permissions of the role corresponding to the id given
     *
     * @param id is an {@link Integer} object
     * @throws {@link BusinessException} the given id doesn't exist in the database
     *                or request is invalid
     */
    @Override
    public Set<PermissionDTO> getRolePermissions(int id) throws BusinessException {
        Role role = roleDao.findRole(id);
        if (role == null)
            throw new BusinessException("msg2_301", "No role was found!");
        return PermissionDTOEntityMapper.getPermissionDTOListFromPermissionList(role.getPermissions());
    }


    /**
     * Sets the new permissions to a user
     * @param permissionsInsertDTO is a {@link PermissionsInsertDTO} object
     *      that maps the {@link Role} object id and the array of {@link PermissionDTO} objects
     * @throws {@link BusinessException} is the corresponding role to the given id doesn't exist in the database
     *      or request is invalid
     */
    @Override
    public void setRolePermissions(PermissionsInsertDTO permissionsInsertDTO) throws BusinessException {
        RoleValidator.validatePermissionsInsertion(permissionsInsertDTO);
        Role role = roleDao.findRole(permissionsInsertDTO.getRoleId());
        if (role == null)
            throw new BusinessException("msg2_301", "No role was found!");

        Set<Permission> actualPermissions = getActualPermissions(permissionsInsertDTO.getPermissions());
        role.setPermissions(actualPermissions);
    }


    /**
     * Returnes a set containing the corresponding {@link Permission} objects from the database
     * using de role type
     *
     * @param permissionDTOS is a set of {@link PermissionDTO} objects
     * @return a set of {@link Permission} object
     */
    private Set<Permission> getActualPermissions(PermissionDTO[] permissionDTOS) {
        Set<Integer> ids = new HashSet<>();
        for (PermissionDTO permissionDTO : permissionDTOS) {
            ids.add(permissionDTO.getId());
        }
        ;

        Set<Permission> actualPermissions = new HashSet<>(permissionDao.getPermissionsByIds(ids));
        return actualPermissions;
    }
}
