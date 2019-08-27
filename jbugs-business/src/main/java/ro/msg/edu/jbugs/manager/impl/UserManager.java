package ro.msg.edu.jbugs.manager.impl;

import org.apache.log4j.Logger;
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

    private Logger logger = Logger.getLogger(UserManager.class.getName());


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
                logger.error(e);
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
    public UserDTO findUser(Integer id) throws BusinessException {
        User user = userDao.findUser(id);
        if (user == null) {
            throw new BusinessException("msg8_10_1101", "No user with this id was found!");
        }
        return UserDTOEntityMapper.getDTOCompleteFromUser(user);
    }

    @Override
    public List<UserDTO> findAllUsers(){
        List<User> users = userDao.findAllUsers();
        return UserDTOEntityMapper.getUserDTOListWithRolesWithoutPasswFromUserList(users);
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

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws BusinessException {
        UserValidator.validate(userDTO);
        User persistedUser = userDao.findUser(userDTO.getId());
        if (persistedUser == null)
            throw new BusinessException("msg8_10_1101", "No user was found!");

        persistedUser.setCounter(userDTO.getCounter());
        persistedUser.setEmail(userDTO.getEmail());
        persistedUser.setFirstName(userDTO.getFirstName());
        persistedUser.setLastName(userDTO.getLastName());
        persistedUser.setMobileNumber(userDTO.getMobileNumber());

        if (!userDTO.getPassword().equals("")) {
            String hashPassword = sha256()
                    .hashString(userDTO.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            persistedUser.setPassword(hashPassword);
        }
        persistedUser.setRoles(getActualRoleList(userDTO.getRoles()));

        if (persistedUser.getStatus() == 0 && userDTO.getStatus() == 1)
            persistedUser.setCounter(0);

        if (persistedUser.getStatus() == 1 && userDTO.getStatus() == 0 && persistedUser.getAssignedBugs().size() > 0)
            persistedUser.setStatus(1);
        else
            persistedUser.setStatus(userDTO.getStatus());

        return UserDTOEntityMapper.getDTOFromUser(persistedUser);
    }

    @Override
    public boolean hasBugsAssigned(Integer id) throws BusinessException {
        User user = userDao.findUser(id);
        if (user == null)
            throw new BusinessException("msg8_10_1102", "No user was found!");
        return user.getAssignedBugs().size() > 0;
    }
}
