package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.dtoEntityMapper.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.NotificationType;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import ro.msg.edu.jbugs.validators.UserValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.hash.Hashing.sha256;

/**
 * Manager class for CRUD actions on {@link User} objects.
 */
@Stateless
public class UserManager implements UserManagerRemote {

    @EJB
    private UserDao userDao;
    @EJB
    private RoleDao roleDao;
    @EJB
    private NotificationManagerRemote notificationManager;

    private Logger logger = Logger.getLogger(UserManager.class.getName());


    /**
     * @param userDTO is an {@link UserDTO} object that maps the {@link User}
     *                object that will be persisted in the database.
     *                Inserts a WELCOME_NEW_USER {@link Notification} object in the database
     * @throws {@link BusinessException} if the {@link UserDTO} object is
     *                invalid
     * @author Mara Corina
     */
    @Override
    public void insertUser(UserDTO userDTO) throws BusinessException {
        UserValidator.validateForAdd(userDTO);
        User persistedUser =  userDao.insertUser(createUserToInsert(userDTO));
        LocalDate date = LocalDate.now();

        NotificationDTO notificationDTO = new NotificationDTO(Date.valueOf(date),  "Welcome: " +  persistedUser.toString(),
                NotificationType.WELCOME_NEW_USER.toString(), "", UserDTOEntityMapper.getDTOFromUser(persistedUser));

        notificationManager.insertNotification(notificationDTO);
    }

    /**
     * Creates the {@link User} object to be persisted
     * @param userDTO is an {@link UserDTO} object that maps the {@link User}
     *                    object that will be persisted in the database.
     * @return a {@link User} object
     * @author Mara Corina
     */
    public User createUserToInsert(UserDTO userDTO) {
        //user's password counter is 0 at insertion
        userDTO.setCounter(0);

        //the user is considered active at insertion
        userDTO.setStatus(1);
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);

        //generate unique username
        String username = generateUsername(user.getFirstName(), user.getLastName());
        user.setUsername(username);

        //generates and hashes password
        String generatedPassword = generatePassword();
        String hashPassword = sha256()
                .hashString(generatedPassword, StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashPassword);

        user.setRoles(getActualRoleList(userDTO.getRoles()));

        return user;
    }


    /**
     * Returnes a set containing the corresponding {@link Role} objects from the database
     *          using de role type
     * @param roleDTOS is a set of {@link RoleDTO} objects
     * @return a set of {@link Role} object
     * @author Mara Corina
     */
    public Set<Role> getActualRoleList(Set<RoleDTO> roleDTOS) {
        Set<RoleType> types = new HashSet<>();
        roleDTOS.forEach(roleDTO -> {
            types.add(roleDTO.getType());
        });

        Set<Role> actualRoles = new HashSet<>(roleDao.getRolesByTypes(types));
        return actualRoles;
    }


    /**
     * Generates a unique username for the ready to insert {@link User} object
     *      using the user firstname and lastname
     * @param firstName and lastName
     *
     * @return {@link String} representing the username
     */
    public String generateUsername(String firstName, String lastName) {

        //uses only the first 5 characters from the lastName
        String firstPart;
        if(lastName.length() >= 5){

            firstPart = lastName.substring(0, 5);
        } else {

            firstPart = lastName;
        }

        //adds characters from the firstName to the username until the username is unique
        //when it reaches the end of the firstName it adds 'x' characters
        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();

        while(!userDao.isUsernameUnique(username)){
            charPosition++;
            if(charPosition < firstName.length()){
                username = (username + firstName.charAt(charPosition)).toLowerCase();
            } else{
                username = username + "x";
            }
        }
        return username;
    }


    /**
     * Generates a password for the ready to insert {@link User} object
     *      using the user firstname and lastname
     *
     * @return {@link String} representing the password
     * @author Mara Corina
     */
    public String generatePassword(){
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return password;
    }

    @Override
    public UserDTO findUser(Integer id) throws BusinessException {
        User user = userDao.findUser(id);
        if (user == null) {
            throw new BusinessException("msg8_10_1101", "No user with this id was found!");
        }
        return UserDTOEntityMapper.getDTOCompleteFromUser(user);
    }

    /**
     * Returns a set of {@link NotificationDTO} objects that wrap the {@link Notification} objects
     * corresponding to the user with the username given as parameter from the database
     *
     * @throws {@link BusinessException} if there is no user with the given username in the database
     */
    @Override
    public Set<NotificationDTO> getUserNotifications(String username) throws BusinessException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new BusinessException("msg8_10_1101", "No user with this id was found!");
        }
        return NotificationDTOEntityMapper.getNotificationDTOListFromNotificationList(user.getNotifications());
    }

    @Override
    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAllUsers();
        return UserDTOEntityMapper.getUserDTOListWithRolesWithoutPasswFromUserList(users);
    }

    @Override
    public LoginResponseUserDTO login(LoginReceivedDTO loginReceivedDTO) {
        LoginResponseUserDTO loginResponseUserDTO;
        try {
            User user = userDao.findByUsernameAndPassword(
                    loginReceivedDTO.getUsername(), loginReceivedDTO.getPassword());
            List<PermissionType> permissions = userDao.getPermissionsOfUser(user);

            Set<String> stringPermissions = new HashSet<>();
            permissions.forEach(permission -> stringPermissions.add(permission.getActualString()));

            UserDTO userDTO = UserDTOEntityMapper.getDTOFromUser(user);
            loginResponseUserDTO = new LoginResponseUserDTO(userDTO, stringPermissions);
            // previous init does NOT set TOKEN on response
            // but sets MESSAGE on 'SUCCESS'
            return loginResponseUserDTO;
        } catch (BusinessException busy) {
            loginResponseUserDTO = new LoginResponseUserDTO(); // init to 0 and null fields... token included
            loginResponseUserDTO.setMessageCode(busy.getErrorCode()); // busy.getMessage()
            return loginResponseUserDTO;
        }
    }

    @Override
    public boolean userHasPermission(Integer userId, String permission) {
        List<PermissionType> permissions = userDao.getPermissionsOfUser(userId);
        Set<String> setPermissions = new HashSet<>();

        permissions.forEach(p -> setPermissions.add(p));
        return setPermissions.contains(permission);
    }

    /**
     * @param userDTO is an {@link UserDTO} object that contains the updated info
     *                of the {@link User} object that will be updated in the database.
     * @return an {@link UserDTO} object with the persisted informations
     * @throws {@link BusinessException} if the {@link UserDTO} object is
     *                invalid or doesn't have a corresponding object in the database
     * @author Mara Corina
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) throws BusinessException {
        UserValidator.validateForUpdate(userDTO);
        User persistedUser = userDao.findUser(userDTO.getId());
        if (persistedUser == null)
            throw new BusinessException("msg8_10_1101", "No user was found!");

        //persist the updated info
        persistedUser.setCounter(userDTO.getCounter());
        persistedUser.setEmail(userDTO.getEmail());
        persistedUser.setFirstName(userDTO.getFirstName());
        persistedUser.setLastName(userDTO.getLastName());
        persistedUser.setMobileNumber(userDTO.getMobileNumber());

        //persist new password
        //if password was not updated, keep the old one
        if (!userDTO.getPassword().equals("")) {
            //hash password
            String hashPassword = sha256()
                    .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            persistedUser.setPassword(hashPassword);
        }

        //take the coresponding roles from the database and persist them
        persistedUser.setRoles(getActualRoleList(userDTO.getRoles()));

        //set the counter to 0 if the user was activated
        if (persistedUser.getStatus() == 0 && userDTO.getStatus() == 1)
            persistedUser.setCounter(0);

        //do not allow deactivation is user has assigned bugs
        if (persistedUser.getStatus() == 1 && userDTO.getStatus() == 0 && persistedUser.getAssignedBugs().size() > 0)
            persistedUser.setStatus(1);
        else
            persistedUser.setStatus(userDTO.getStatus());

        return UserDTOEntityMapper.getDTOFromUser(persistedUser);
    }

    /**
     * The method checks if the user with the specified id has any bugs assigned
     * @param id is an {@link Integer}
     * @return {@link Boolean}
     * @throws {@link BusinessException} if there's no corresponding object in the database
     * @author Mara Corina
     */
    @Override
    public boolean hasBugsAssigned(Integer id) throws BusinessException {
        User user = userDao.findUser(id);
        if (user == null)
            throw new BusinessException("msg8_10_1102", "No user was found!");
        return user.getAssignedBugs().size() > 0;
    }
}
