package ro.msg.edu.jbugs.manager.remote;

import ro.msg.edu.jbugs.dto.LoginReceivedDTO;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Remote;
import java.util.List;

/**
 * Interface for Remote usage
 *
 * @author Mara Corina
 */
@Remote
public interface UserManagerRemote {

    void insertUser(UserDTO userDTO) throws BusinessException;

    UserDTO findUser(Integer id) throws BusinessException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(UserDTO userDTO) throws BusinessException;

    boolean hasBugsAssigned(Integer id) throws BusinessException;

    LoginResponseUserDTO login(LoginReceivedDTO loginReceivedDTO);

    boolean userHasPermission(Integer userId, String permission);
}
