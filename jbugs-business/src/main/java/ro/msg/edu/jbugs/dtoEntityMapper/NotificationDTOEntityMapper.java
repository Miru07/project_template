package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.entity.Notification;

/**
 * Entity Mapper class for {@link Notification} & {@link NotificationDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Mara Corina
 */
public class NotificationDTOEntityMapper {

    private NotificationDTOEntityMapper(){

    }

    public static Notification getNotificationFromDTO(NotificationDTO notificationDTO){

        Notification notification = new Notification();

        if (notificationDTO != null) {
            notification.setID(notificationDTO.getID());
            notification.setDate(notificationDTO.getDate());
            notification.setMessage(notificationDTO.getMessage());
            notification.setType(notificationDTO.getType());
            notification.setUrl(notificationDTO.getUrl());
            notification.setUser(notificationDTO.getUser_id());
        }

        return notification;
    }

    public static NotificationDTO getDTOFromNotification(Notification notification){

        NotificationDTO notificationDTO = new NotificationDTO();

        if (notification != null) {
            notificationDTO.setID(notification.getID());
            notificationDTO.setDate(notification.getDate());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setType(notification.getType());
            notificationDTO.setUrl(notification.getUrl());
            notificationDTO.setUser_id(notification.getUser());
        }

        return notificationDTO;

    }
}
