package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.NotificationType;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.hash.Hashing.*;

@Stateless
public class UserManager implements UserManagerRemote {

    @EJB
    private UserDao userDao;
    @EJB
    private NotificationManagerRemote notificationManager;

    @Override
    public void insertUser(UserDTO userDTO) {

        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        String username = generateUsername(user.getFirstName(), user.getLastName());
        user.setUsername(username);


        String hashPassword = sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashPassword);

        User newUser = userDao.insertUser(user);
        UserDTO userDTO1 = UserDTOEntityMapper.getDTOFromUser(newUser);
        userDTO1.setPassword(userDTO.getPassword());
        LocalDate date = LocalDate.now();

        NotificationDTO notificationDTO = new NotificationDTO(Date.valueOf(date), "Welcome: " + newUser.toString(),
                NotificationType.WELCOME_NEW_USER.toString(), "", newUser);

        notificationManager.insertNotification(notificationDTO);
    }

    String generateUsername(String firstName, String lastName) {

        String firstPart;
        if (lastName.length() >= 5) {

            firstPart = lastName.substring(0, 5);
        } else {

            firstPart = lastName;
        }

        int charPosition = 0;
        String username = (firstPart + firstName.charAt(charPosition)).toLowerCase();

        while (!userDao.isUsernameUnique(username)) {

            charPosition++;
            if (charPosition < firstName.length()) {

                username = (username + firstName.charAt(charPosition)).toLowerCase();
            } else {

                username = username + "x";
            }
        }
        return username;
    }

    @Override
    public UserDTO findUser(Integer id) {

        User user = userDao.findUser(id);
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public List<UserDTO> findAllUsers() {

        List<User> users = userDao.findAllUsers();
        return UserDTOEntityMapper.getUserDTOListFromUserList(users);
    }

    @Override
    public List<UserBugsDTO> getUserBugs() {

        List<UserBugsDTO> userBugsDTOList = new ArrayList<>();
        List<Object[]> userBugsList = userDao.findUserBugs();

        for (Object[] userBug : userBugsList) {

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
