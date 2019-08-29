package authorization.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.manager.impl.UserManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {
    @InjectMocks
    UserManager userManager;
    @Mock
    UserDao userDao;

    @Test
    public void generateLoginToken() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        loginResponseUserDTO.setUsername("diana");
        loginResponseUserDTO.setId(1);

        String token = TokenService.generateLoginToken(loginResponseUserDTO);
        assertNotNull(token);
    }
    @Test(expected = NullPointerException.class)
    public void generateLoginToken2() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        TokenService.generateLoginToken(loginResponseUserDTO);
    }

    @Test
    public void decodeJWT() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        loginResponseUserDTO.setUsername("diana");
        loginResponseUserDTO.setId(1);

        String token = TokenService.generateLoginToken(loginResponseUserDTO);
        Integer id = TokenService.getCurrentUserID(token); //uses decrypt
        String username = TokenService.getCurrentUserUSERNAME(token); //uses decrypt

        assertEquals(loginResponseUserDTO.getId(), id);
        assertEquals(loginResponseUserDTO.getUsername(), username);
    }

    @Test
    public void currentUserHasPermission() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        loginResponseUserDTO.setUsername("diana");
        loginResponseUserDTO.setId(1);

        String token = TokenService.generateLoginToken(loginResponseUserDTO);

        List<PermissionType> permissionsLIST = new ArrayList<>();
        permissionsLIST.add(PermissionType.BUG_CLOSE);
        permissionsLIST.add(PermissionType.BUG_MANAGEMENT);
        permissionsLIST.add(PermissionType.USER_MANAGEMENT);

        Set<String> permissionsSET = new HashSet<>();
        permissionsLIST.forEach(permission -> permissionsSET.add(permission.getActualString()));

        loginResponseUserDTO.setPermissions(permissionsSET);

        when(userDao.getPermissionsOfUser(1)).thenReturn(permissionsLIST);

        assertFalse(TokenService.currentUserHasPermission(userManager, token,
                PermissionType.PERMISSION_MANAGEMENT));
        assertTrue(TokenService.currentUserHasPermission(userManager, token,
                PermissionType.USER_MANAGEMENT));
        assertTrue(TokenService.currentUserHasPermission(userManager, token,
                PermissionType.BUG_CLOSE));
        assertTrue(TokenService.currentUserHasPermission(userManager, token,
                PermissionType.BUG_MANAGEMENT));
    }

    @Test
    public void getCurrentUserID() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        loginResponseUserDTO.setUsername("diana");
        loginResponseUserDTO.setId(1);
        String token = TokenService.generateLoginToken(loginResponseUserDTO);
        assertEquals(loginResponseUserDTO.getId(), TokenService.getCurrentUserID(token));
    }

    @Test
    public void getCurrentUserUSERNAME() {
        LoginResponseUserDTO loginResponseUserDTO = new LoginResponseUserDTO();
        loginResponseUserDTO.setUsername("diana");
        loginResponseUserDTO.setId(1);
        String token = TokenService.generateLoginToken(loginResponseUserDTO);
        assertEquals(loginResponseUserDTO.getUsername(), TokenService.getCurrentUserUSERNAME(token));
    }
}
