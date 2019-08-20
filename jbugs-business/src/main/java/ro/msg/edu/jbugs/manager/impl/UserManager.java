package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserBugsDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
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
import java.util.List;

import static com.google.common.hash.Hashing.*;

@Stateless
public class UserManager implements UserManagerRemote {

    @EJB
    private UserDao userDao;
    @EJB
    private NotificationManagerRemote notificationManager;

    @Override
    public void insertUser(UserDTO userDTO){

        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        String username = generateUsername(user.getFirstName(), user.getLastName());
        user.setUsername(username);
        String hashPassword = sha256()
                .hashString(user.getPassword(), StandardCharsets.UTF_8)
                .toString();
        user.setPassword(hashPassword);

        User newUser =  userDao.insertUser(user);
        UserDTO userDTO1 = UserDTOEntityMapper.getDTOFromUser(newUser);
        userDTO1.setPassword(userDTO.getPassword());
        LocalDate date = LocalDate.now();

        NotificationDTO notificationDTO = new NotificationDTO(Date.valueOf(date),  "Welcome: " +  newUser.toString(),
                NotificationType.WELCOME_NEW_USER.toString() , "", newUser);

      notificationManager.insertNotification(notificationDTO);
    }

    String generateUsername(String firstName, String lastName){

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

    @Override
    public UserDTO findUser(Integer id){

        User user = userDao.findUser(id);
        return UserDTOEntityMapper.getDTOFromUser(user);
    }

    @Override
    public List<UserDTO> findAllUsers(){

        List<User> users = userDao.findAllUsers();
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

//    @Override
//    public Integer deleteUser(Integer userID){
//
//        notificationManager.deleteNotification(userID);
//
//        return userDao.deleteUser(userID);
//    }

    @Override
    public UserDTO login(String username, String password) throws BusinessException {
        return UserDTOEntityMapper.getDTOFromUser(userDao.findByUsernameAndHashedPass(username, password));
    }

    public UserDTO login2(String username, String password) throws BusinessException{

        User userToLogin = userDao.findUserByUsername(username);

        if(userToLogin != null){

            String hashPassword = sha256().hashString(password, StandardCharsets.UTF_8).toString();
            if(hashPassword.equals(userToLogin.getPassword())){

                if(userToLogin.getStatus() == 0){

                    throw new BusinessException("msg-003", "User is disabled");
                }
                else
                {
                    userToLogin.setCounter(0);
                    return UserDTOEntityMapper.getDTOFromUser(userToLogin);
                }
            }
            else
            {

                int userCounter = userToLogin.getCounter() + 1;
                userToLogin.setCounter(userCounter);
                if(userCounter == 5){
                    userToLogin.setStatus(0);
                    throw new BusinessException("msg-004", "Max number of tries");
                }
            }
        }
        else
        {
            throw new BusinessException("msg-005", "User not in DB");
        }

        return null;
    }

}
