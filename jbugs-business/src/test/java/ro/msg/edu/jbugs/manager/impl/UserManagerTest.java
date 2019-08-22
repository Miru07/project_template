package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.UserDTOEntityMapper;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @InjectMocks
    private UserManager userManager;

    @Mock
    private UserDao userDao;

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
        when(userDao.findByUsernameAndHashedPass("dinum", "parola")).thenThrow(BusinessException.class);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("test5");
        loginReceivedDTO.setHashedPassword("test5");
        userManager.login(loginReceivedDTO);
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

    @Test(expected = BusinessException.class)
    public void login3() throws BusinessException {

        when(userDao.findUserByUsername("dinum")).thenReturn(null);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("test5");
        loginReceivedDTO.setHashedPassword("test5");
        userManager.login(loginReceivedDTO);
    }

    @Test
    public void login4() throws BusinessException{

        User persistedUser = createUser();
        when(userDao.findUserByUsername("test5")).thenReturn(persistedUser);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("test5");
        loginReceivedDTO.setHashedPassword("test5");
        userManager.login(loginReceivedDTO);
        assertEquals((Integer)2, persistedUser.getCounter());
    }

    @Test
    public void login5() throws BusinessException{

        User persistedUser = createUser();
        when(userDao.findUserByUsername("test5")).thenReturn(persistedUser);
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO();
        loginReceivedDTO.setUsername("test5");
        loginReceivedDTO.setHashedPassword("test5");
        LoginResponseUserDTO loginResponseUserDTO = userManager.login(loginReceivedDTO);

        assertEquals(persistedUser.getFirstName(), loginResponseUserDTO.getFirstName());
        assertEquals(persistedUser.getLastName(), loginResponseUserDTO.getLastName());
        // assertEquals(1L, loginResponseUserDTO.getId());
        assertEquals(persistedUser.getEmail(), loginResponseUserDTO.getEmail());
        assertEquals(persistedUser.getMobileNumber(), loginResponseUserDTO.getMobileNumber());
        // assertEquals(persistedUser.getPassword(), loginResponseUserDTO.getPassword());
        assertEquals(persistedUser.getUsername(), loginResponseUserDTO.getUsername());
    }
}
