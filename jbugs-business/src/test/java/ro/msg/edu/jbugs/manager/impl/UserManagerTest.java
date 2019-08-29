package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.NotificationDao;
import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.dtoEntityMapper.NotificationDTOEntityMapper;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.NotificationType;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Test Class for {@link UserManager}
 *
 * @author Mara Corina
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private NotificationDao notificationDao;

    @Mock
    private NotificationManager notificationManager;

    @Mock
    private BusinessException businessException;

    public UserManagerTest() {

        userManager = new UserManager();
    }

    @Test
    public void generateUsername() {

        //when(userDao.isUsernameUnique(Mockito.anyString())).thenReturn(true);
        when(userDao.isUsernameUnique("dinum")).thenReturn(true);
        when(userDao.isUsernameUnique("baloz")).thenReturn(true);

        assertEquals("dinum", userManager.generateUsername("Miruna", "Dinu"));
        assertEquals("baloz", userManager.generateUsername("Zsolt", "Balo"));

        Mockito.verify(userDao, Mockito.times(2)).isUsernameUnique(anyString());

        when(userDao.isUsernameUnique("dinutm")).thenReturn(true);

        assertEquals("dinutm", userManager.generateUsername("Miruna", "Dinuttt"));
    }

    @Test
    public void generateUsername2() {

        when(userDao.isUsernameUnique("baloz")).thenReturn(false);
        when(userDao.isUsernameUnique("balozs")).thenReturn(false);
        when(userDao.isUsernameUnique("balozso")).thenReturn(false);
        when(userDao.isUsernameUnique("balozsol")).thenReturn(false);
        when(userDao.isUsernameUnique("balozsolt")).thenReturn(false);
        when(userDao.isUsernameUnique("balozsoltx")).thenReturn(false);
        when(userDao.isUsernameUnique("balozsoltxx")).thenReturn(true);

        assertEquals("balozsoltxx", userManager.generateUsername("Zsolt", "Balo"));
    }


    private User createUser(){

        User user = new User(1, 0, "Corina", "Mara", "0743170363",
                "mara@msggroup.com", "marac", "test", 1);

        return user;
    }

    private User createUserLogIn() {
        User user = new User(1, 0, "Corina", "Mara", "0743170363", "mara.corina@msggroup.com", "marac", "test",1);
        return user;
    }

    private UserDTO createUserDTO() {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO(RoleType.ADMINISTRATOR));
        UserDTO userDTO = new UserDTO(1, "Corina", "Mara", "marac", "test",
                0, "mara@msggroup.com", "0743170363", 1, roleDTOS);
        return userDTO;
    }

    @Test
    public void loginNoToken() throws BusinessException {
        //noinspection unchecked
        when(userDao.findByUsernameAndPassword("dinum", "parola")).thenThrow(BusinessException.class);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("dinum");
        loginReceivedDTO.setPassword("wrongparola");
        assertEquals(userManager.login(loginReceivedDTO).getToken(), "");
    }

    @Test
    public void loginExceptionFromDAO() throws BusinessException {
        BusinessException businessException = new BusinessException("LOGIN-001", "");
        // for username not found / inactive / wrong passw / deactivated
        when(userDao.findByUsernameAndPassword("dinum", "parola")).thenThrow(businessException);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("dinum");
        loginReceivedDTO.setPassword("parola");
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceivedDTO);
        assertEquals(loginResponseUserDTO.getMessageCode(), businessException.getErrorCode());
    }
    @Test
    public void loginPermissions() throws BusinessException {
        User user = createUserLogIn();
        when(userDao.findByUsernameAndPassword(anyString(), anyString())).thenReturn(user);
        List<PermissionType> permissions = new ArrayList<>();
        permissions.add(PermissionType.PERMISSION_MANAGEMENT);
        when(userDao.getPermissionsOfUser(user)).thenReturn(permissions);
        Set<PermissionType> permissionsSet = new HashSet<>();
        permissions.forEach(p -> permissionsSet.add(p));
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername(user.getUsername());
        loginReceivedDTO.setPassword(user.getPassword());
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceivedDTO);
        assertEquals(loginReceivedDTO.getUsername(), loginResponseUserDTO.getUsername());
        assertEquals(loginResponseUserDTO.getPermissions().toArray()[0], PermissionType.PERMISSION_MANAGEMENT.getActualString());
    }

    @Test
    public void loginResponseFields() throws BusinessException {
        User persistedUser = createUser();
        when(userDao.findByUsernameAndPassword("dinum", "test5")).thenReturn(persistedUser);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("dinum");
        loginReceivedDTO.setPassword("test5");
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceivedDTO);
        assertEquals(persistedUser.getFirstName(), loginResponseUserDTO.getFirstName());
        assertEquals(persistedUser.getLastName(), loginResponseUserDTO.getLastName());
        assertEquals(persistedUser.getEmail(), loginResponseUserDTO.getEmail());
        assertEquals(persistedUser.getMobileNumber(), loginResponseUserDTO.getMobileNumber());
        assertEquals(persistedUser.getUsername(), loginResponseUserDTO.getUsername());
    }
    // updated in refactoring branch @Diana


    @Test
    public void getActualRoleList() throws BusinessException{
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO(RoleType.ADMINISTRATOR));
        Role role = new Role(1, RoleType.ADMINISTRATOR);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Set<RoleType> types = new HashSet<>();
        types.add(RoleType.ADMINISTRATOR);
        when(roleDao.getRolesByTypes(types)).thenReturn(roles);

        Set<Role> actualRoles = userManager.getActualRoleList(roleDTOS);

        assertTrue(actualRoles.contains(role));
    }

    @Test
    public void createUserToInsert() throws BusinessException {
        UserDTO userDTO = UserDTOEntityMapper.getDTOFromUser(createUser());
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO(RoleType.ADMINISTRATOR));
        userDTO.setRoles(roleDTOS);

        Role role = new Role(1, RoleType.ADMINISTRATOR);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Set<RoleType> types = new HashSet<>();
        types.add(RoleType.ADMINISTRATOR);
        when(roleDao.getRolesByTypes(types)).thenReturn(roles);

        when(userDao.isUsernameUnique("marac")).thenReturn(true);

        User newUser = userManager.createUserToInsert(userDTO);

        assertEquals((Integer)newUser.getCounter(), (Integer)0);
        assertEquals((Integer)newUser.getStatus(), (Integer)1);
        assertEquals(newUser.getUsername(), "marac");
        assertTrue(newUser.getRoles().contains(role));
    }


    @Test
    public void insertUserTestSuccess() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        User persistedUser = createUser();
        when(userDao.insertUser(Matchers.any(User.class))).thenReturn(persistedUser);

        Role role = new Role(1, RoleType.ADMINISTRATOR);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Set<RoleType> types = new HashSet<>();
        types.add(RoleType.ADMINISTRATOR);
        when(roleDao.getRolesByTypes(types)).thenReturn(roles);

        when(userDao.isUsernameUnique("marac")).thenReturn(true);

        NotificationDTO notificationDTO = new NotificationDTO(Date.valueOf(LocalDate.now()), "Welcome: " + persistedUser.toString(),
                NotificationType.WELCOME_NEW_USER.toString(), "", persistedUser);
        Notification notification = NotificationDTOEntityMapper.getNotificationFromDTO(notificationDTO);
        when(notificationDao.insertNotification(Matchers.any(Notification.class))).thenReturn(notification);

        userManager.insertUser(userDTO);
    }

    @Test
    public void updateUserTestSuccess() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setStatus(0);
        userDTO.setCounter(4);
        Role role = new Role(1, RoleType.ADMINISTRATOR);
        when(roleDao.findRoleByType(RoleType.ADMINISTRATOR)).thenReturn(role);

        when(userDao.findUser(1)).thenReturn(UserDTOEntityMapper.getUserFromUserDTO(userDTO));

        userDTO.setUsername("test");
        userDTO.setFirstName("NewName");
        userDTO.setStatus(1);
        userDTO.setPassword("");

        UserDTO updatedUser = userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailNull() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        when(userDao.findUser(1)).thenReturn(null);
        userManager.updateUser(userDTO);
    }

    @Test
    public void hasBugsAssignedSuccess() throws BusinessException {
        User user = createUser();
        Set<Bug> bugs = new HashSet<>();
        Bug bug = new Bug();
        bug.setCREATED_ID(user);
        bug.setASSIGNED_ID(user);
        bugs.add(bug);
        user.setAssignedBugs(bugs);
        when(userDao.findUser(1)).thenReturn(user);
        assertEquals(userManager.hasBugsAssigned(1), true);

        user.setAssignedBugs(new HashSet<>());
        assertEquals(userManager.hasBugsAssigned(1), false);
    }

    @Test(expected = BusinessException.class)
    public void hasBugsAssignedFailNull() throws BusinessException {
        when(userDao.findUser(1)).thenReturn(null);
        userManager.hasBugsAssigned(1);
    }

    @Test
    public void findUserSuccess() throws BusinessException {
        User user = createUser();
        when(userDao.findUser(1)).thenReturn(user);
        UserDTO userDTO = userManager.findUser(1);
        assertEquals(userDTO.getId(), 1);
    }

    @Test(expected = BusinessException.class)
    public void findUserFailNull() throws BusinessException {
        when(userDao.findUser(1)).thenReturn(null);
        userManager.findUser(1);
    }

    @Test
    public void findAllUsers() {
        User user = createUser();
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userDao.findAllUsers()).thenReturn(users);
        assertEquals(userManager.findAllUsers().size(), 1);
    }

    @Test
    public void getUserNotificationsSuccess() throws BusinessException {
        User user = createUser();
        Set<Notification> notifications = new HashSet<>();
        Notification notification = new Notification(1, new java.sql.Date(Calendar.getInstance().getTime().getTime()), "Welcome:", NotificationType.WELCOME_NEW_USER.toString(), "", user);
        notifications.add(notification);
        user.setNotifications(notifications);

        when(userDao.findUserByUsername("marac")).thenReturn(user);
        assertEquals(userManager.getUserNotifications("marac").size(), 1);
    }

    @Test(expected = BusinessException.class)
    public void getUserNotificationsNull() throws BusinessException {
        when(userDao.findUserByUsername("marac")).thenReturn(null);
        userManager.getUserNotifications("marac");
    }
}
