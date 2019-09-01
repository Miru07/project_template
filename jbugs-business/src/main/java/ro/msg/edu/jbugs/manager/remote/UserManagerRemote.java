package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;
import java.util.Set;

/**
 * Interface for Remote usage
 *
 * @author Mara Corina
 */
@Remote
public interface UserManagerRemote {

    void insertUser(UserDTO userDTO) throws BusinessException;

    UserDTO findUser(Integer id) throws BusinessException;

    Set<NotificationDTO> getUserNotifications(String username) throws BusinessException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(UserUpdateDTO userUpdateDTO) throws BusinessException;

    boolean hasBugsAssigned(Integer id) throws BusinessException;

    LoginResponseUserDTO login(LoginReceivedDTO loginReceivedDTO) throws BusinessException;

    boolean userHasPermission(Integer userId, PermissionType permission);

    Set<NotificationDTO> getUserTodayNotifications(String username) throws BusinessException;

    Set<NotificationDTO> getUserNewNotificationsById(String username, Integer idLastNotification) throws BusinessException;
}
