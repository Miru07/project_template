package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity Mapper class for {@link User} & {@link UserDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 */
public class UserDTOEntityMapper {


    private UserDTOEntityMapper(){

    }

    public static User getUserFromUserDTO(UserDTO userDTO){

        User user = new User();
        if (userDTO != null) {
            user.setID(userDTO.getId());
            user.setCounter(userDTO.getCounter());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setStatus(userDTO.getStatus());
        }

        return user;
    }

    public static UserDTO getDTOFromUser(User user){
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setCounter(user.getCounter());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setMobileNumber(user.getMobileNumber());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setStatus(user.getStatus());
            userDTO.setRoles(RoleDTOEntityMapper.getRoleDTOListFromRoleList(user.getRoles()));
            userDTO.setId(user.getID());
        }

        return userDTO;
    }

    public static UserDTO getDTOCompleteFromUser(User user) {
        UserDTO userDTO = getDTOFromUser(user);
        if (user != null) {
            userDTO.setPassword(user.getPassword());
        }

        return userDTO;
    }

    /***************************************VIEW_USERS********************************************
     *
     * @param user
     * @return UserDTO without password, with array of roles
     */
    public static UserDTO getDTOWithRolesWithoutPasswFromUser(User user){
        UserDTO userDTO = getDTOFromUser(user);
        userDTO.setPassword("");
        return userDTO;
    }

    /***************************************VIEW_USERS********************************************
     *
     * @param users
     * @return List of UserDTO without passwords and with roles of each
     */
    public static List<UserDTO> getUserDTOListWithRolesWithoutPasswFromUserList(List<User> users){
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach(user -> userDTOList.add(getDTOWithRolesWithoutPasswFromUser(user)));
        return userDTOList;
    }

    public static List<UserDTO> getUserDTOListFromUserList(List<User> users){

        List<UserDTO> userDTOList = new ArrayList<>();

        for(User u : users){

            userDTOList.add(getDTOFromUser(u));
        }

        return userDTOList;
    }


    public static List<User> getUserListFromDTOList(List<UserDTO> userDTOList) {

        List<User> userList = new ArrayList<>();

        for (UserDTO dto : userDTOList) {

            userList.add(getUserFromUserDTO(dto));
        }

        return userList;
    }

}
