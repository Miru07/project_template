package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.Set;

/**
 * Validator class for {@link User} class.
 *
 * @author Mara Corina
 * @since 19.1.2
 */
public class UserValidator {
    /**
     * @param user is an {@link UserDTO} object that contains the user info
     *             that needs to be validated
     * @throws {@link BusinessException} if the object is  not valid to be updated in the database
     */
    public static void validateForUpdate(UserDTO user) throws BusinessException {
        validateForAdd(user);
        if (!validateCounter(user.getCounter())) {
            throw new BusinessException("msg406", "Invalid counter");
        }
        if (!validateStatus(user.getStatus())) {
            throw new BusinessException("msg407", "Invalid status");
        }
        if (!validatePassword(user.getPassword())) {
            throw new BusinessException("msg408", "Invalid password");
        }
    }

    /**
     * @param user is an {@link UserDTO} object that contains the user info
     *             that needs to be validated
     * @throws {@link BusinessException} if the object is  not valid to be added to the database
     */
    public static void validateForAdd(UserDTO user) throws BusinessException {
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
        /* The RegEx matches msg email addressed
         */
        return email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@msggroup.com$");
    }

    static boolean validatePhoneNumber(String phonenumber) {
        /* The RegEx matches phone numbers from Romania and Germany
         */
        return phonenumber.matches("((^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|-)?([0-9]{3}(\\s|\\.|-|)){2}$)|(\\(?\\+\\(?49\\)?[()]?([()]?\\d[()]?){10}))");
    }

    static boolean validateName(String name) {
        /* The RegEx matches names starting with a capital letter, containing only letters, with
        length betwwen 2 and 15
         */
        return (name.length() > 2 && name.length() < 15) && name.matches("^[A-ZÜÄÖÂÎĂȚȘ][a-zA-Zșțăîâäöüß]*");
    }

    static boolean validatePassword(String password) {
        /* Password must have length between 2 and 15
            Password cand be null for cases when it was not updated
         */
        return (password.length() > 3 && password.length() < 20) || password.length() == 0;
    }

    static boolean validateRoles(Set<RoleDTO> roles) {
        /* A user must have at least one role
            All roles must have a correct type
         */
        for (RoleDTO roleDTO : roles) {
            if (!roleDTO.getType().equals("ADMINISTRATOR")
                    && !roleDTO.getType().equals("PROJECT_MANAGER")
                    && !roleDTO.getType().equals("TEST_MANAGER")
                    && !roleDTO.getType().equals("DEVELOPER")
                    && !roleDTO.getType().equals("TESTER"))
                return false;
        }
        return roles.size() > 0;
    }

    static boolean validateCounter(Integer count) {
        /* Counter must be between 0 and 5
            5 is the maximum number of tries
         */
        return count > -1 && count < 6;
    }

    static boolean validateStatus(Integer state) {
        return state == 1 || state == 0;
    }
}
