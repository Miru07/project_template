package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.NotificationDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.entity.types.StatusType;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationManagerTest {
    @InjectMocks
    private NotificationManager notificationManager;

    @Mock
    private NotificationDao notificationDao;

    private UserDTO createUserDTO() {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO(RoleType.ADMINISTRATOR));
        UserDTO userDTO = new UserDTO(1, "Corina", "Mara", "marac", "test",
                0, "mara@msggroup.com", "0743170363", 1, roleDTOS);
        return userDTO;
    }

    public BugDTO createBugDTO() {
        BugDTO bugDTO = new BugDTO(0, "test", "test", "1.1.1",
                new Date(2019, 1, 1), "NEW", "", "LOW",
                createUserDTO(), createUserDTO());
        String string = "test";

        return bugDTO;
    }

    public NotificationManagerTest() {

        notificationManager = new NotificationManager();
    }

    @Test
    public void insertWelcomeNotificationTest() {
        UserDTO userDTO = createUserDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertWelcomeNotification(userDTO);
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertDeletedUserNotificationTest() {
        UserDTO userDTO = createUserDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertDeletedUserNotification(userDTO, new UserDTO());
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));

    }

    @Test
    public void insertDeactivatedUserNotificationTest() {
        UserDTO userDTO = createUserDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertDeactivatedUserNotification(userDTO, new UserDTO());
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertUserUpdatedNotificationTest() {
        UserDTO userDTO = createUserDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertUserUpdatedNotification(userDTO, userDTO, new UserDTO());
        Mockito.verify(notificationDao, Mockito.times(2)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertBugStatusUpdatedNotificationTest() {
        BugDTO bugDTO = createBugDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertBugStatusUpdatedNotification(bugDTO, StatusType.FIXED);
        Mockito.verify(notificationDao, Mockito.times(2)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertBugStatusUpdatedNotificationTest2() {
        BugDTO bugDTO = createBugDTO();
        bugDTO.setASSIGNED_ID(null);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertBugStatusUpdatedNotification(bugDTO, StatusType.FIXED);
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertBugUpdatedNotificationTest() {
        BugDTO bugDTO = createBugDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertBugUpdatedNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(2)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertBugUpdatedNotificationTest2() {
        BugDTO bugDTO = createBugDTO();
        bugDTO.setASSIGNED_ID(null);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertBugUpdatedNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));
    }

    @Test
    public void insertNewBugNotificationTest() {
        BugDTO bugDTO = createBugDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertNewBugNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(2)).insertNotification(Matchers.any(Notification.class));

    }

    @Test
    public void insertNewBugNotificationTest2() {
        BugDTO bugDTO = createBugDTO();
        bugDTO.setASSIGNED_ID(null);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertNewBugNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));

    }

    @Test
    public void insertClosedBugNotificationTest() {
        BugDTO bugDTO = createBugDTO();
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertClosedBugNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(2)).insertNotification(Matchers.any(Notification.class));

    }

    @Test
    public void insertClosedBugNotificationTest2() {
        BugDTO bugDTO = createBugDTO();
        bugDTO.setASSIGNED_ID(null);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(new Notification());
        notificationManager.insertClosedBugNotification(bugDTO);
        Mockito.verify(notificationDao, Mockito.times(1)).insertNotification(Matchers.any(Notification.class));

    }

    @Test
    public void insertNotificationTest() {
        Notification notification = new Notification();
        notification.setID(1);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(notification);
        assertEquals((Integer) notificationManager
                .insertNotification(NotificationDTOEntityMapper
                        .getDTOFromNotification(notification)).getID(), (Integer) 1);
    }
}