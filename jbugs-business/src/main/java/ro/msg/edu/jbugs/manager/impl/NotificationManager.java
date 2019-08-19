package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.NotificationDao;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class NotificationManager implements NotificationManagerRemote {

    @EJB
    private NotificationDao notificationDao;

    public NotificationDTO insertNotification(NotificationDTO notification){

        notificationDao.insertNotification(NotificationDTOEntityMapper.getNotificationFromDTO(notification));
        return notification;
    }

    @Override
    public Integer deleteNotification(Integer userID) {

        return notificationDao.deleteNotification(userID);
    }
}
