package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.NotificationDTO;

import javax.ejb.Remote;

@Remote
public interface NotificationManagerRemote {

    NotificationDTO insertNotification(NotificationDTO notification);
    Integer deleteNotification(Integer userID);
}
