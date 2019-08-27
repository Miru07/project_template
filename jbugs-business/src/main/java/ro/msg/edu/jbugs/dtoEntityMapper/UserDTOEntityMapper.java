package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserDTOEntityMapper {


    private UserDTOEntityMapper(){

    }

    public static User getUserFromUserDTO(UserDTO userDTO){

        User user = new User(userDTO.getCounter(), userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getMobileNumber(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getStatus());

        user.setID(userDTO.getId());
        return user;
    }

    public static UserDTO getDTOFromUser(User user){
        UserDTO userDTO = new UserDTO(user.getCounter(), user.getFirstName(), user.getLastName(),
                user.getMobileNumber(), user.getEmail(), user.getUsername(), user.getStatus());
        userDTO.setRoles(RoleDTOEntityMapper.getRoleDTOListFromRoleList(user.getRoles()));

        userDTO.setId(user.getID());
        return userDTO;
    }

    public static UserDTO getDTOCompleteFromUser(User user) {
        UserDTO userDTO = new UserDTO(user.getID(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(),
                user.getCounter(), user.getEmail(), user.getMobileNumber(), user.getStatus(), new HashSet<>());
        userDTO.setRoles(RoleDTOEntityMapper.getRoleDTOListFromRoleList(user.getRoles()));

        userDTO.setId(user.getID());
        return userDTO;
    }

    public static List<UserDTO> getUserDTOListFromUserList(List<User> users){

        List<UserDTO> userDTOList = new ArrayList<>();

        for(User u : users){

            userDTOList.add(getDTOFromUser(u));
        }

        return userDTOList;
    }

//    public static List<User> getUserListFromDTOList(List<UserDTO> userDTOList){
//
//        List<User> userList = new ArrayList<>();
//
//        for(UserDTO dto : userDTOList){
//
//            userList.add(getUserFromUserDTO(dto));
//        }
//
//        return userList;
//    }

}
