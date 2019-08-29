package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.*;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.*;

import static org.junit.Assert.*;
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
        User user = new User(1, 0, "Corina", "Mara", "0743170363", "mara.corina@msggroup.com", "marac", "test", 1);

        return user;
    }

    private UserDTO createUserDTO() {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("ADMINISTRATOR"));
        UserDTO userDTO = new UserDTO(1, "Corina", "Mara", "marac", "test", 0, "mara@msggroup.com", "0743170363", 1, roleDTOS);
        return userDTO;
    }

    // updated in refactoring branch @Diana

    @Test
    public void getActualRoleList() throws BusinessException{
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("ADMINISTRATOR"));
        Role role = new Role(1, "ADMINISTRATOR");
        when(roleDao.findRoleByType("ADMINISTRATOR")).thenReturn(role);

        Set<Role> actualRoles = userManager.getActualRoleList(roleDTOS);

        assertTrue(actualRoles.contains(role));

        roleDTOS.clear();
        roleDTOS.add(new RoleDTO("wrong role"));
        when(roleDao.findRoleByType("wrong role")).thenThrow(new BusinessException("test", "test"));
        actualRoles = userManager.getActualRoleList(roleDTOS);
        assertEquals(actualRoles.size(), 0);
    }

    @Test
    public void createUserToInsert() throws BusinessException {
        UserDTO userDTO = UserDTOEntityMapper.getDTOFromUser(createUser());
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("ADMINISTRATOR"));
        userDTO.setRoles(roleDTOS);

        Role role = new Role(1, "ADMINISTRATOR");
        when(roleDao.findRoleByType("ADMINISTRATOR")).thenReturn(role);

        when(userDao.isUsernameUnique("marac")).thenReturn(true);

        User newUser = userManager.createUserToInsert(userDTO);

        assertEquals((Integer)newUser.getCounter(), (Integer)0);
        assertEquals((Integer)newUser.getStatus(), (Integer)1);
        assertEquals(newUser.getUsername(), "marac");
        assertTrue(newUser.getRoles().contains(role));
    }


    @Test(expected = BusinessException.class)
    public void insertUserTestFailFirstnameIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("df34");
        userManager.insertUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void insertUserTestFailLastnameIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("dff34");
        userManager.insertUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void insertUserTestFailPhoneNumberIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setMobileNumber("456787654323456789876543456");
        userManager.insertUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void insertUserTestFailEmailIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setEmail("mara@yahoo.com");
        userManager.insertUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void insertUserTestFailRolesIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setRoles(new HashSet<>());
        userManager.insertUser(userDTO);
    }

    @Test
    public void updateUserTestSuccess() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setStatus(0);
        userDTO.setCounter(4);
        Role role = new Role(1, "ADMINISTRATOR");
        when(roleDao.findRoleByType("ADMINISTRATOR")).thenReturn(role);

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

    @Test(expected = BusinessException.class)
    public void updateUserTestFailFirstnameIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("df34");
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailPasswordIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setFirstName("x");
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailLastnameIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setLastName("dff34");
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailPhoneNumberIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setMobileNumber("456787654323456789876543456");
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailEmailIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setEmail("mara@yahoo.com");
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailRolesIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setRoles(new HashSet<>());
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailRolesIncorrect2() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("Incorrect"));
        userDTO.setRoles(roleDTOS);
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailCounterIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setCounter(10);
        userManager.updateUser(userDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateUserTestFailStatusIncorrect() throws BusinessException {
        UserDTO userDTO = createUserDTO();
        userDTO.setStatus(10);
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
