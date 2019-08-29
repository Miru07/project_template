package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.entity.Notification;

import java.util.HashSet;
import java.util.Set;

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
        notification.setUser(UserDTOEntityMapper.getUserFromUserDTO(notificationDTO.getUser()));

        return notification;
    }

    public static NotificationDTO getDTOFromNotification(Notification notification){

        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setID(notification.getID());
        notificationDTO.setDate(notification.getDate());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setType(notification.getType());
        notificationDTO.setUrl(notification.getUrl());
        notificationDTO.setUser(UserDTOEntityMapper.getDTOFromUser(notification.getUser()));

        return notificationDTO;

    }

    public static Set<NotificationDTO> getNotificationDTOListFromNotificationList(Set<Notification> notifications) {
        Set<NotificationDTO> notificationDTOS = new HashSet<>();

        for (Notification notification : notifications) {
            notificationDTOS.add(getDTOFromNotification(notification));
        }

        return notificationDTOS;
    }
}
