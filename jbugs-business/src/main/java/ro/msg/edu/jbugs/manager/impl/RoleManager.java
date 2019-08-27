package ro.msg.edu.jbugs.manager.impl;

import org.apache.log4j.Logger;
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
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class RoleManager implements RoleManagerRemote {

    @EJB
    private RoleDao roleDao;

    @EJB
    private PermissionDao permissionDao;

    @EJB
    private NotificationManagerRemote notificationManager;

    private Logger logger = Logger.getLogger(RoleManager.class.getName());

    @Override
    public Set<PermissionDTO> getRolePermissions(int id) throws BusinessException {
        Role role = roleDao.findRole(id);
        if (role == null)
            throw new BusinessException("msg2_301", "No role was found!");
        return PermissionDTOEntityMapper.getPermissionDTOListFromPermissionList(role.getPermissions());
    }

    @Override
    public void setRolePermissions(PermissionsInsertDTO permissionsInsertDTO) throws BusinessException {
        RoleValidator.validatePermissionsInsertion(permissionsInsertDTO);
        Role role = roleDao.findRole(permissionsInsertDTO.getRoleId());
        if (role == null)
            throw new BusinessException("msg2_301", "No role was found!");

        Set<Permission> actualPermissions = new HashSet<>();
        for (PermissionDTO permissionDTO : permissionsInsertDTO.getPermissions()) {
            actualPermissions.add(permissionDao.findPermission(permissionDTO.getId()));
        }
        role.setPermissions(actualPermissions);
    }
}
