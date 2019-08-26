package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.entity.Bug;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Mapper class for {@link Bug} & {@link BugDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Sebastian Maier
 */
public class BugDTOEntityMapper {
    @EJB
    private UserDao userDao;

    private BugDTOEntityMapper(){

    }

    public static Bug getBugWithUsersWithoutRoles(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(UserDTOEntityMapper.getUserFromUserDTOWithoutRoles(bugDTO.getASSIGNED_ID()));
        //bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTOWithoutRoles(bugDTO.getCREATED_ID()));

        return bug;
    }

    public static Bug getBug(BugDTO bugDTO){
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID()));
        //bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID()));

        return bug;
    }

    public static BugDTO getBugDTO(Bug bug){
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getID());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setSeverity(bug.getSeverity());

        bugDTO.setASSIGNED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getASSIGNED_ID()));
        bugDTO.setCREATED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getCREATED_ID()));

        return bugDTO;
    }

    public static List<BugDTO> getBugDTOList(List<Bug> bugList){

        List<BugDTO> bugDTOList = new ArrayList<>();

        for(Bug b: bugList){
            bugDTOList.add(getBugDTO(b));
        }

        return bugDTOList;
    }
}
