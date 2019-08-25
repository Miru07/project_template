package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.RoleDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.RoleDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


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

    @Test(expected = BusinessException.class)
    public void login() throws BusinessException {

        //noinspection unchecked
        when(userDao.findUserByUsernameAndPassword("dinum", "parola")).thenThrow(BusinessException.class);
        userManager.login("dinum", "parola");
    }

    private User createUser(){

        User user = new User();
        user.setID(1);
        user.setFirstName("test5");
        user.setLastName("test5");
        user.setEmail("test5");
        user.setCounter(1);
        user.setMobileNumber("123456");
        user.setUsername("test5t");
        user.setPassword("a140c0c1eda2def2b830363ba362aa4d7d255c262960544821f556e16661b6ff");
        user.setStatus(1);

        return user;
    }

    private UserDTO createUserDTO() {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("Administrator"));
        UserDTO userDTO = new UserDTO(1, "Corina", "Mara", "marac", "test", 0, "mara@msggroup.com", "0743170363", 1, roleDTOS);
        return userDTO;
    }

    @Test(expected = BusinessException.class)
    public void login3() throws BusinessException {

        when(userDao.findUserByUsername("dinum")).thenReturn(null);
        userManager.login("dinum", "parola");
    }

    @Test
    public void login4() throws BusinessException{

        User persistedUser = createUser();
        when(userDao.findUserByUsername("test5")).thenReturn(persistedUser);
        userManager.login("test5", "wrong");
        assertEquals((Integer)2, persistedUser.getCounter());
    }

    @Test
    public void login5() throws BusinessException{

        User persistedUser = createUser();
        when(userDao.findUserByUsername("test5")).thenReturn(persistedUser);
        UserDTO userDTO = userManager.login("test5", "test5");

        assertEquals(persistedUser.getFirstName(), userDTO.getFirstName());
        assertEquals(persistedUser.getLastName(), userDTO.getLastName());
        assertEquals(1L, userDTO.getId());
        assertEquals(persistedUser.getEmail(), userDTO.getEmail());
        assertEquals(persistedUser.getMobileNumber(), userDTO.getMobileNumber());
        assertEquals(persistedUser.getPassword(), userDTO.getPassword());
        assertEquals(persistedUser.getUsername(), userDTO.getUsername());
    }

    @Test
    public void getActualRoleList() throws BusinessException{
        Set<RoleDTO> roleDTOS = new HashSet<>();
        roleDTOS.add(new RoleDTO("Administrator"));
        Role role = new Role(1, "Administrator");
        when(roleDao.findRoleByType("Administrator")).thenReturn(role);

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
        roleDTOS.add(new RoleDTO("Administrator"));
        userDTO.setRoles(roleDTOS);

        Role role = new Role(1, "Administrator");
        when(roleDao.findRoleByType("Administrator")).thenReturn(role);

        when(userDao.isUsernameUnique("test5t")).thenReturn(true);

        User newUser = userManager.createUserToInsert(userDTO);

        assertEquals((Integer)newUser.getCounter(), (Integer)0);
        assertEquals((Integer)newUser.getStatus(), (Integer)1);
        assertEquals(newUser.getUsername(), "test5t");
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
}