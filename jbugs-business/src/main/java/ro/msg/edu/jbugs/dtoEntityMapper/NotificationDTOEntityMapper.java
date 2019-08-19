package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.entity.Notification;

public class NotificationDTOEntityMapper {

    private NotificationDTOEntityMapper(){

    }

    public static Notification getNotificationFromDTO(NotificationDTO notificationDTO){

        Notification notification = new Notification();

        notification.setID(notificationDTO.getID());
        notification.setDate(notificationDTO.getDate());
        notification.setMessage(notificationDTO.getMessage());
        notification.setType(notificationDTO.getType());
        notification.setUrl(notificationDTO.getUrl());
        notification.setUser(notificationDTO.getUser_id());

        return notification;
    }

    public static NotificationDTO getDTOFromNotification(Notification notification){

        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setID(notification.getID());
        notificationDTO.setDate(notification.getDate());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setType(notification.getType());
        notificationDTO.setUrl(notification.getUrl());
        notificationDTO.setUser_id(notification.getUser());

        return notificationDTO;

    }
}
