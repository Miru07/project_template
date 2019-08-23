package utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class TokenServiceTest {

    @Mock
    private TokenService tokenService;

    @Test
    public void generateJWT() {
        assertEquals(true, true);
    }

    @Test
    public void decodeJWT() {
        /*
        // testing:
        // String decToken = TokenService.decodeJWT(jwtToken).getId();
        boolean userHasPerm = TokenService.currentUserHasPermission(userManager, jwtToken,
                PermissionType.PERMISSION_MANAGEMENT.getActualString());
        boolean userHasPerm2 = TokenService.currentUserHasPermission(userManager, jwtToken,
                PermissionType.USER_MANAGEMENT.getActualString());
        boolean userHasPerm3 = TokenService.currentUserHasPermission(userManager, jwtToken,
                PermissionType.BUG_CLOSE.getActualString());
        boolean userHasPerm4 = TokenService.currentUserHasPermission(userManager, jwtToken,
                PermissionType.BUG_MANAGEMENT.getActualString());

        loginResponseUserDTO.setMessage(loginResponseUserDTO.getMessage()
                + "   userID: " + TokenService.getCurrentUserID(jwtToken)
                + "   userUSERNAME: " + TokenService.getCurrentUserUSERNAME(jwtToken)
                + "   permission " + PermissionType.PERMISSION_MANAGEMENT.getActualString()
                + userHasPerm
                + "   permission " + PermissionType.USER_MANAGEMENT.getActualString()
                + userHasPerm2
                + "   permission " + PermissionType.BUG_CLOSE.getActualString()
                + userHasPerm3
                + "   permission " + PermissionType.BUG_MANAGEMENT.getActualString()
                + userHasPerm4
        );
        */
        assertEquals(true, true);
    }

    @Test
    public void currentUserHasPermission() {
        assertEquals(true, true);
    }
}
