package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.dto.UserBugsDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserManagerRemote {

    void insertUser(UserDTO userDTO);
    UserDTO findUser(Integer id);
    List<UserDTO> findAllUsers();
    List<UserBugsDTO> getUserBugs();
    //Integer deleteUser(Integer userID);
    LoginResponseUserDTO login(LoginReceivedDTO loginReceivedDTO);
}
