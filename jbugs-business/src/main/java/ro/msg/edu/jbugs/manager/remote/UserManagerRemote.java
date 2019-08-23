package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.UserBugsDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserManagerRemote {

    void insertUser(UserDTO userDTO) throws BusinessException;
    UserDTO findUser(Integer id);
    List<UserDTO> findAllUsers();
    List<UserBugsDTO> getUserBugs();
    //Integer deleteUser(Integer userID);
    UserDTO login(String username, String password) throws BusinessException;
}
