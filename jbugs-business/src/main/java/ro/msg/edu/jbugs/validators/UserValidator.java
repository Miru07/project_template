package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserValidator {
    public static void validate(UserDTO user) throws BusinessException {
        if (!validateName(user.getFirstName())) {
            throw new BusinessException("msg401", "Firstname invalid");
        }
        if (!validateName(user.getLastName())) {
            throw new BusinessException("msg402", "Lastname invalid");
        }
        if (!validatePhoneNumber(user.getMobileNumber())) {
            throw new BusinessException("msg403", "Phone number invalid");
        }
        if (!validateEmail(user.getEmail())) {
            throw new BusinessException("msg404", "Invalid Email");
        }
        if (!validateRoles(user.getRoles())) {
            throw new BusinessException("msg405", "Invalid Roles");
        }
    }

    static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@msggroup.com$");
    }

    static boolean validatePhoneNumber(String phonenumber) {
        return phonenumber.matches("((^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|-)?([0-9]{3}(\\s|\\.|-|)){2}$)|(\\(?\\+\\(?49\\)?[()]?([()]?\\d[()]?){10}))");
    }

    static boolean validateName(String name) {
        return (name.length() > 2 && name.length() < 15) && name.matches("^[A-ZÜÄÖÂÎĂȚȘ][a-zA-Zșțăîâäöüß]*");
    }

    static boolean validateRoles(Set<RoleDTO> roles) {
        for (RoleDTO roleDTO : roles) {
            if (!roleDTO.getType().equals("Administrator")
                    && !roleDTO.getType().equals("Project Manager")
                    && !roleDTO.getType().equals("Test Manager")
                    && !roleDTO.getType().equals("Developer")
                    && !roleDTO.getType().equals("Tester"))
                return false;
        }
        return roles.size() > 0;
    }
}
