package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.NotificationType;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import ro.msg.edu.jbugs.validators.UserValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static com.google.common.hash.Hashing.sha256;

@Stateless
public class UserManager implements UserManagerRemote {

    @EJB
    private UserDao userDao;
    @EJB
    private RoleDao roleDao;
    @EJB
    private NotificationManagerRemote notificationManager;

    //private Logger logger = Logger.getLogger(UserManager.class.getName());


    @Override
    public void insertUser(UserDTO userDTO) throws BusinessException {
        UserValidator.validate(userDTO);
        User persistedUser =  userDao.insertUser(createUserToInsert(userDTO));
        LocalDate date = LocalDate.now();

        NotificationDTO notificationDTO = new NotificationDTO(Date.valueOf(date),  "Welcome: " +  persistedUser.toString(),
                NotificationType.WELCOME_NEW_USER.toString() , "", persistedUser);

        notificationManager.insertNotification(notificationDTO);
    }

    public User createUserToInsert(UserDTO userDTO){
        userDTO.setCounter(0);
        userDTO.setStatus(1);
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        String username = generateUsername(user.getFirstName(), user.getLastName());
        user.setUsername(username);

        String generatedPassword = generatePassword();
        String hashPassword = sha256()
                .hashString(generatedPassword, StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashPassword);

        user.setRoles(getActualRoleList(userDTO.getRoles()));

        return user;
    }
    public Set<Role> getActualRoleList(Set<RoleDTO> roleDTOS){
        Set<Role> actualRoles = new HashSet<>();
        roleDTOS.forEach(roleDTO -> {
            try {
                actualRoles.add(roleDao.findRoleByType(roleDTO.getType()));
            } catch (BusinessException e) {
                //logger.error(e);
                return;
            }
        });
        return actualRoles;
    }

    public String generateUsername(String firstName, String lastName){

        String firstPart;
        if(lastName.length() >= 5){

            firstPart = lastName.substring(0, 5);
        }
        else {

            firstPart = lastName;
        }

        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();

        while(!userDao.isUsernameUnique(username)){
            charPosition++;
            if(charPosition < firstName.length()){
                username = (username + firstName.charAt(charPosition)).toLowerCase();
            }
            else{
                username = username + "x";
            }
        }
        return username;
    }

    public String generatePassword(){
        String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return password;
    }

    @Override
    public UserDTO findUser(Integer id){
        User user = userDao.findUser(id);
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAllUsers();

        users.forEach(System.out::println);

        return UserDTOEntityMapper.getUserDTOListFromUserList(users);
    }

    @Override
    public List<UserBugsDTO> getUserBugs(){
        List<UserBugsDTO> userBugsDTOList = new ArrayList<>();
        List<Object[]> userBugsList = userDao.findUserBugs();

        for(Object[] userBug : userBugsList){

            Long value = (Long) userBug[2];
            UserBugsDTO userBugsDTO = new UserBugsDTO(userBug[0].toString(), userBug[1].toString(), value.intValue());
            userBugsDTOList.add(userBugsDTO);
        }
        return userBugsDTOList;
    }

    @Override
    public LoginResponseUserDTO login(LoginReceivedDTO loginReceivedDTO) {
        LoginResponseUserDTO loginResponseUserDTO;
        try {
            User user = userDao.findByUsernameAndPassword(
                    loginReceivedDTO.getUsername(), loginReceivedDTO.getPassword());
            List<String> permissions = userDao.getPermissionsOfUser(user);

            Set<String> stringPermissions = new HashSet<>();
            permissions.forEach(permission -> stringPermissions.add(permission));

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
    public boolean userHasPermission(Integer userId, String permission){
        List<String> permissions = userDao.getPermissionsOfUser(userId);
        return permissions.contains(permission);
    }
}
