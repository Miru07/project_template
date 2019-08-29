package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.PermissionDao;
import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dto.PermissionDTO;
import ro.msg.edu.jbugs.dto.PermissionsInsertDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.PermissionDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test Class for {@link RoleManager}
 *
 * @author Mara Corina
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleManagerTest {

    @InjectMocks
    private RoleManager roleManager;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PermissionDao permissionDao;


    public RoleManagerTest() {
        roleManager = new RoleManager();
    }

    @Test(expected = BusinessException.class)
    public void getRolePermissionsTestFail() throws BusinessException {
        when(roleDao.findRole(1)).thenReturn(null);
        roleManager.getRolePermissions(1);
    }

    @Test
    public void getRolePermissionsTestSuccess() throws BusinessException {
        Role role = new Role(1, RoleType.ADMINISTRATOR);
        Set<Permission> permissions = new HashSet<>();
        Permission permission = new Permission(1, "", PermissionType.PERMISSION_MANAGEMENT);
        permissions.add(permission);
        role.setPermissions(permissions);

        when(roleDao.findRole(1)).thenReturn(role);
        assertEquals(roleManager.getRolePermissions(1).size(), 1);
    }

    @Test
    public void setRolePermissionsTestSuccess() throws BusinessException {
        Role role = new Role(1, RoleType.ADMINISTRATOR);
        Set<Permission> permissions = new HashSet<>();
        Permission permission = new Permission(1, "", PermissionType.PERMISSION_MANAGEMENT);
        permissions.add(permission);
        Set<PermissionDTO> permissionDTOS = PermissionDTOEntityMapper.getPermissionDTOListFromPermissionList(permissions);
        PermissionDTO[] permissionDTOS1 = new PermissionDTO[permissionDTOS.size()];
        int index = 0;
        for (PermissionDTO permissionDTO : permissionDTOS) {
            permissionDTOS1[index] = permissionDTO;
            index++;
        }

        when(roleDao.findRole(1)).thenReturn(role);
        when(permissionDao.findPermission(1)).thenReturn(permission);
        roleManager.setRolePermissions(new PermissionsInsertDTO(1, permissionDTOS1));
    }

    @Test(expected = BusinessException.class)
    public void setRolePermissionsTestFailNullRole() throws BusinessException {
        Set<Permission> permissions = new HashSet<>();
        Permission permission = new Permission(1, "", PermissionType.PERMISSION_MANAGEMENT);
        permissions.add(permission);
        Set<PermissionDTO> permissionDTOS = PermissionDTOEntityMapper.getPermissionDTOListFromPermissionList(permissions);
        PermissionDTO[] permissionDTOS1 = new PermissionDTO[permissionDTOS.size()];
        int index = 0;
        for (PermissionDTO permissionDTO : permissionDTOS) {
            permissionDTOS1[index] = permissionDTO;
            index++;
        }


        when(roleDao.findRole(1)).thenReturn(null);
        roleManager.setRolePermissions(new PermissionsInsertDTO(1, permissionDTOS1));
    }
}