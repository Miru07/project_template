package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.Permission;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.Set;

/**
 * Manager class for CRUD actions on {@link User} & {@link Permission}.
 *
 * @author Mara Corina
 */
public class UserValidator {
    /**
     * @param user is an {@link UserDTO} object that contains the user info
     *             that needs to be validated
     * @throws {@link BusinessException} if the object is  not valid to be updated in the database
     */
    public static void validateForUpdate(UserDTO user) throws BusinessException {
        validateForAdd(user);
        validateCounter(user.getCounter());
        validateStatus(user.getStatus());
        validatePassword(user.getPassword());
    }

    /**
     * @param user is an {@link UserDTO} object that contains the user info
     *             that needs to be validated
     * @throws {@link BusinessException} if the object is  not valid to be added to the database
     */
    public static void validateForAdd(UserDTO user) throws BusinessException {
        validateFirstname(user.getFirstName());
        validateLastname(user.getLastName());
        validatePhoneNumber(user.getMobileNumber());
        validateEmail(user.getEmail());
        validateRoles(user.getRoles());
    }


    private static void validateEmail(String email) throws BusinessException {
        /* The RegEx matches msg email addressed
         */
        if (!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@msggroup.com$"))
            throw new BusinessException("msg404", "Invalid Email");
    }

    private static void validatePhoneNumber(String phonenumber) throws BusinessException {
        /* The RegEx matches phone numbers from Romania and Germany
         */
        if (!phonenumber.matches("((^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|-)?([0-9]{3}(\\s|\\.|-|)){2}$)|(\\(?\\+\\(?49\\)?[()]?([()]?\\d[()]?){10}))"))
            throw new BusinessException("msg403", "Phone number invalid");
    }

    private static void validateFirstname(String firstname) throws BusinessException {
        /* The RegEx matches names starting with a capital letter, containing only letters, with
        length betwwen 2 and 15
         */
        if (!((firstname.length() > 2 && firstname.length() < 15) && firstname.matches("^[A-ZÜÄÖÂÎĂȚȘ][a-zA-Zșțăîâäöüß]*")))
            throw new BusinessException("msg402", "Firstname invalid");
    }

    private static void validateLastname(String lastname) throws BusinessException {
        /* The RegEx matches names starting with a capital letter, containing only letters, with
        length betwwen 2 and 15
         */
        if (!((lastname.length() > 2 && lastname.length() < 15) && lastname.matches("^[A-ZÜÄÖÂÎĂȚȘ][a-zA-Zșțăîâäöüß]*")))
            throw new BusinessException("msg402", "Lastname invalid");
    }

    private static void validatePassword(String password) throws BusinessException {
        /* Password must have length between 2 and 15
            Password cand be null for cases when it was not updated
         */
        if (!((password.length() > 3 && password.length() < 20) || password.length() == 0))
            throw new BusinessException("msg408", "Invalid password");
    }

    private static void validateRoles(Set<RoleDTO> roles) throws BusinessException {
        /* A user must have at least one role
            All roles must have a correct type
         */
        for (RoleDTO roleDTO : roles) {
            if (!roleDTO.getType().equals(RoleType.ADMINISTRATOR)
                    && !roleDTO.getType().equals(RoleType.PROJECT_MANAGER)
                    && !roleDTO.getType().equals(RoleType.TEST_MANAGER)
                    && !roleDTO.getType().equals(RoleType.DEVELOPER)
                    && !roleDTO.getType().equals(RoleType.TESTER))
                throw new BusinessException("msg405", "Invalid Roles");
        }
        if (roles.size() <= 0)
            throw new BusinessException("msg405", "Invalid Roles");
    }

    private static void validateCounter(Integer count) throws BusinessException {
        /* Counter must be between 0 and 5
            5 is the maximum number of tries
         */
        if (!(count > -1 && count < 6))
            throw new BusinessException("msg406", "Invalid counter");
    }

    private static void validateStatus(Integer state) throws BusinessException {
        if (!(state == 1 || state == 0))
            throw new BusinessException("msg407", "Invalid status");
    }
}
