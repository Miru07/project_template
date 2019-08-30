package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.types.StatusType;

import javax.ejb.Remote;

@Remote
public interface NotificationManagerRemote {

    NotificationDTO insertNotification(NotificationDTO notification);

    void insertWelcomeNotification(UserDTO userDTO);

    void insertDeletedUserNotification(UserDTO userDTO, UserDTO receiverDTO);

    void insertDeactivatedUserNotification(UserDTO userDTO, UserDTO receiverDTO);

    void insertUserUpdatedNotification(UserDTO newUserDTO, UserDTO oldUserDTO, UserDTO updaterDTO);

    void insertBugStatusUpdatedNotification(BugDTO bugDTO, StatusType oldStatus);

    void insertBugUpdatedNotification(BugDTO bugDTO);

    void insertNewBugNotification(BugDTO bugDTO);

    void insertClosedBugNotification(BugDTO bugDTO);

    Integer deleteNotification(Integer userID);
}
