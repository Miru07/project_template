
package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.NotificationDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.NotificationType;
import ro.msg.edu.jbugs.entity.types.StatusType;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import java.sql.Date;
import java.time.LocalDate;

@Stateless
public class NotificationManager implements NotificationManagerRemote {

    @EJB
    private NotificationDao notificationDao;

    public NotificationDTO insertNotification(NotificationDTO notification){

        notificationDao.insertNotification(NotificationDTOEntityMapper.getNotificationFromDTO(notification));
        return notification;
    }

    public Notification createNewNotification(NotificationType type, String message, String url, User user) {
        Notification notification = new Notification(Date.valueOf(LocalDate.now()), message, type, url, user);

        return notification;
    }

    @Override
    public void insertWelcomeNotification(UserDTO userDTO) {
        String rolesToString = "";
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            rolesToString += roleDTO.getType() + ", ";
        }
        ;
        String message = "Welcome, " + userDTO.getFirstName() + " " + userDTO.getLastName() + "!" + '\n' +
                '\n' +
                "Email : " + userDTO.getEmail() + '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Roles : " + rolesToString + '\n' +
                "Username : " + userDTO.getUsername() + '\n';
        User user = UserDTOEntityMapper.getUserFromUserDTO(userDTO);
        Notification notification = createNewNotification(NotificationType.WELCOME_NEW_USER, message, "", user);

        notificationDao.insertNotification(notification);
    }

    @Override
    public void insertDeletedUserNotification(UserDTO userDTO, UserDTO receiverDTO) {
        String rolesToString = "";
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            rolesToString += roleDTO.getType() + ", ";
        }
        ;
        String message = "User " + userDTO.getUsername() + " was deleted!" + '\n' +
                '\n' +
                "Email : " + userDTO.getEmail() + '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Roles : " + rolesToString + '\n' +
                "Username : " + userDTO.getUsername() + '\n';
        User receiver = UserDTOEntityMapper.getUserFromUserDTO(receiverDTO);
        Notification notification = createNewNotification(NotificationType.USER_DELETED, message, "", receiver);

        notificationDao.insertNotification(notification);
    }

    @Override
    public void insertDeactivatedUserNotification(UserDTO userDTO, UserDTO admin) {
        String rolesToString = "";
        for (RoleDTO roleDTO : userDTO.getRoles()) {
            rolesToString += roleDTO.getType() + ", ";
        }

        String message = "User " + userDTO.getUsername() + " was deactivated!" + '\n' +
                '\n' +
                "Email : " + userDTO.getEmail() + '\n' +
                "First Name : " + userDTO.getFirstName() + '\n' +
                "Last Name : " + userDTO.getLastName() + '\n' +
                "Mobile Number : " + userDTO.getMobileNumber() + '\n' +
                "Roles : " + rolesToString + '\n' +
                "Username : " + userDTO.getUsername() + '\n';
        User receiver = UserDTOEntityMapper.getUserFromUserDTO(admin);
        Notification notification = createNewNotification(NotificationType.USER_DEACTIVATED, message, "", receiver);

        notificationDao.insertNotification(notification);
    }

    @Override
    public void insertUserUpdatedNotification(UserDTO newUserDTO, UserDTO oldUserDTO, UserDTO updaterDTO) {
        String newRolesToString = "";
        for (RoleDTO roleDTO : newUserDTO.getRoles()) {
            newRolesToString += roleDTO.getType() + ", ";
        }
        ;
        String oldRolesToString = "";
        for (RoleDTO roleDTO : oldUserDTO.getRoles()) {
            oldRolesToString += roleDTO.getType() + ", ";
        }
        ;
        String message = "User account updated!" + '\n' +
                "NEW INFO " + '\n' +
                "\tEmail : " + newUserDTO.getEmail() + '\n' +
                "\tFirst Name : " + newUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + newUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + newUserDTO.getMobileNumber() + '\n' +
                "\tRoles : " + newRolesToString + '\n' +
                "\tUsername : " + newUserDTO.getUsername() + '\n' +
                '\n' +
                "OLD INFO " + '\n' +
                "\tEmail : " + oldUserDTO.getEmail() + '\n' +
                "\tFirst Name : " + oldUserDTO.getFirstName() + '\n' +
                "\tLast Name : " + oldUserDTO.getLastName() + '\n' +
                "\tMobile Number : " + oldUserDTO.getMobileNumber() + '\n' +
                "\tRoles : " + oldRolesToString + '\n' +
                "\tUsername : " + oldUserDTO.getUsername() + '\n';

        User user = UserDTOEntityMapper.getUserFromUserDTO(newUserDTO);
        Notification notificationUser = createNewNotification(NotificationType.USER_UPDATED, message, "", user);

        User updater = UserDTOEntityMapper.getUserFromUserDTO(updaterDTO);
        Notification notificationUpdater = createNewNotification(NotificationType.USER_UPDATED, message, "", updater);

        notificationDao.insertNotification(notificationUser);
        notificationDao.insertNotification(notificationUpdater);
    }

    @Override
    public void insertBugStatusUpdatedNotification(BugDTO bugDTO, StatusType oldStatus) {
        String assignedUsername = "";
        if (bugDTO.getASSIGNED_ID().getId() != 0)
            assignedUsername = bugDTO.getASSIGNED_ID().getUsername();
        String message = "Bug " + bugDTO.getID() + " updated status!" + '\n' +
                '\n' +
                "New status : " + bugDTO.getStatus() + '\n' +
                "Old Status : " + oldStatus + '\n' +
                "Title : " + bugDTO.getTitle() + '\n' +
                "Description : " + bugDTO.getDescription() + '\n' +
                "Version : " + bugDTO.getVersion() + '\n' +
                "Target Date : " + bugDTO.getTargetDate() + '\n' +
                "Severity : " + bugDTO.getSeverity() + '\n' +
                "Created by : " + bugDTO.getCREATED_ID().getUsername() + '\n' +
                "Assigned to : " + assignedUsername + '\n';
        User creatorUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID());
        Notification notification1 = createNewNotification(NotificationType.BUG_STATUS_UPDATED, message,
                "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), creatorUser);
        notificationDao.insertNotification(notification1);

        if (bugDTO.getASSIGNED_ID().getId() != 0) {
            User assignationUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID());
            Notification notification2 = createNewNotification(NotificationType.BUG_STATUS_UPDATED, message,
                    "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), assignationUser);
            notificationDao.insertNotification(notification2);
        }
    }

    @Override
    public void insertBugUpdatedNotification(BugDTO bugDTO) {
        String assignedUsername = "";
        if (bugDTO.getASSIGNED_ID().getId() != 0)
            assignedUsername = bugDTO.getASSIGNED_ID().getUsername();
        String message = "Bug " + bugDTO.getID() + " was updated!" + '\n' +
                '\n' +
                "Status : " + bugDTO.getStatus() + '\n' +
                "Title : " + bugDTO.getTitle() + '\n' +
                "Description : " + bugDTO.getDescription() + '\n' +
                "Version : " + bugDTO.getVersion() + '\n' +
                "Target Date : " + bugDTO.getTargetDate() + '\n' +
                "Severity : " + bugDTO.getSeverity() + '\n' +
                "Created by : " + bugDTO.getCREATED_ID().getUsername() + '\n' +
                "Assigned to : " + assignedUsername + '\n';
        User creatorUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID());
        Notification notification1 = createNewNotification(NotificationType.BUG_UPDATED, message,
                "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), creatorUser);
        notificationDao.insertNotification(notification1);

        if (bugDTO.getASSIGNED_ID().getId() != 0) {
            User assignationUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID());
            Notification notification2 = createNewNotification(NotificationType.BUG_UPDATED, message,
                    "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), assignationUser);
            notificationDao.insertNotification(notification2);
        }
    }

    @Override
    public void insertNewBugNotification(BugDTO bugDTO) {
        String assignedUsername = "";
        if (bugDTO.getASSIGNED_ID().getId() != 0)
            assignedUsername = bugDTO.getASSIGNED_ID().getUsername();
        String message = "Bug " + bugDTO.getID() + " is new!" + '\n' +
                '\n' +
                "Title : " + bugDTO.getTitle() + '\n' +
                "Description : " + bugDTO.getDescription() + '\n' +
                "Version : " + bugDTO.getVersion() + '\n' +
                "Target Date : " + bugDTO.getTargetDate() + '\n' +
                "Severity : " + bugDTO.getSeverity() + '\n' +
                "Status : " + bugDTO.getStatus() + '\n' +
                "Created by : " + bugDTO.getCREATED_ID().getUsername() + '\n' +
                "Assigned to : " + assignedUsername + '\n';
        User creatorUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID());
        Notification notification1 = createNewNotification(NotificationType.BUG_UPDATED, message,
                "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), creatorUser);
        notificationDao.insertNotification(notification1);

        if (bugDTO.getASSIGNED_ID().getId() != 0) {
            User assignationUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID());
            Notification notification2 = createNewNotification(NotificationType.BUG_UPDATED, message,
                    "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), assignationUser);
            notificationDao.insertNotification(notification2);
        }
    }

    @Override
    public void insertClosedBugNotification(BugDTO bugDTO) {
        String assignedUsername = "";
        if (bugDTO.getASSIGNED_ID().getId() != 0)
            assignedUsername = bugDTO.getASSIGNED_ID().getUsername();
        String message = "Bug " + bugDTO.getID() + " is closed!" + '\n' +
                '\n' +
                "Title : " + bugDTO.getTitle() + '\n' +
                "Description : " + bugDTO.getDescription() + '\n' +
                "Version : " + bugDTO.getVersion() + '\n' +
                "Target Date : " + bugDTO.getTargetDate() + '\n' +
                "Severity : " + bugDTO.getSeverity() + '\n' +
                "Status : " + bugDTO.getStatus() + '\n' +
                "Created by : " + bugDTO.getCREATED_ID().getUsername() + '\n' +
                "Assigned to : " + assignedUsername + '\n';
        User creatorUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID());
        Notification notification1 = createNewNotification(NotificationType.BUG_CLOSED, message,
                "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), creatorUser);
        notificationDao.insertNotification(notification1);

        if (bugDTO.getASSIGNED_ID().getId() != 0) {
            User assignationUser = UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID());
            Notification notification2 = createNewNotification(NotificationType.BUG_CLOSED, message,
                    "http://localhost:4200/dashboard/notifications/bugs/view/" + bugDTO.getID(), assignationUser);
            notificationDao.insertNotification(notification2);
        }
    }

    @Override
    @Schedule(hour = "*", minute = "*/1", second = "*", info = "Every hour notifications cleaner")
    public void clean() {
        notificationDao.deleteOlderThanOneMonth();
    }
}




