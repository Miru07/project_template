package ro.msg.edu.jbugs.validators;

import org.junit.Test;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.HashSet;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserValidatorTest {

    private UserDTO createUserDTO() {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO(RoleType.ADMINISTRATOR));
        UserDTO userDTO = new UserDTO(1, "Corina", "Mara", "marac", "test",
                0, "mara@msggroup.com", "0743170363", 1, roleDTOS);
        return userDTO;
    }

    @Test
    public void validateForUpdateSuccess1() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        UserValidator.validateForUpdate(userDTO);
    }

    @Test
    public void validateForUpdateSuccess2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setPassword("");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test
    public void validateForUpdateSuccess3() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setStatus(0);
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectFirstname1() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("gresit");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectFirstname2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("Gresit1");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectFirstname3() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("G");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectLastname1() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("gresit");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectLastname2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("Gresit1");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectLastname3() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("G");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectPassword() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setPassword("xx");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectCounter1() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setCounter(8);
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectCounter2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setCounter(-1);
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectEmail() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setEmail("mara@yahoo.com");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectPhone() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setMobileNumber("0745353908045");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectStatus() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setMobileNumber("6");
        UserValidator.validateForUpdate(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForUpdateIncorrectRoles2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        Set<RoleDTO> roleDTOS = new HashSet<>();
        userDTO.setRoles(roleDTOS);
        UserValidator.validateForUpdate(userDTO);
    }

    @Test
    public void validateForAddSuccess() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        UserValidator.validateForAdd(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForAddIncorrectFirstname() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("gresit");
        UserValidator.validateForAdd(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void validateForAddIncorrectLastname() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("gresit");
        UserValidator.validateForAdd(userDTO);
    }

}