package ro.msg.edu.jbugs.dtoEntityMapper;

import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.entity.Bug;

import javax.ejb.EJB;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity Mapper class for {@link Bug} & {@link BugDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Sebastian Maier
 */
public class BugDTOEntityMapper {
    @EJB
    private UserDao userDao;

    private BugDTOEntityMapper() {

    }

    public static Bug getBugWithoutUsers(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity().toUpperCase());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(null);
        bug.setCREATED_ID(null);

        return bug;
    }

    public static Bug getBugWithUserCreatedAndAssigned(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity().toUpperCase());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID()));
        bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID()));

        return bug;
    }

    public static Bug getBug(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity().toUpperCase());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID()));
        bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID()));

        return bug;
    }

    public static BugDTO getBugDTO(Bug bug) {
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

    public static List<BugDTO> getBugDTOList(List<Bug> bugList) {

        return bugList.stream().map(BugDTOEntityMapper::getBugDTO).collect(Collectors.toList());
    }

    public static BugDTO getBugDTOWithoutAssigned(Bug bug) {
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getID());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity().toUpperCase());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setVersion(bug.getVersion());

        bugDTO.setASSIGNED_ID(null);
        bugDTO.setCREATED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getCREATED_ID()));

        return bugDTO;
    }

    public static BugDTO getBugDTOForUpdate(Bug bug) {
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getID());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity().toUpperCase());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setCREATED_ID(UserDTOEntityMapper.getDTOFromUserWithPass(bug.getCREATED_ID()));
        bugDTO.setASSIGNED_ID((UserDTOEntityMapper.getDTOFromUserWithPass(bug.getASSIGNED_ID())));

        return bugDTO;
    }

    public static Bug getBugWithoutUserAssigned(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity().toUpperCase());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(null);
        bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID()));

        return bug;
    }

    public static Bug getBugWithoutUserCreated(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setID(bugDTO.getID());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity().toUpperCase());
        bug.setStatus(bugDTO.getStatus());
        bug.setVersion(bugDTO.getVersion());

        bug.setASSIGNED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getASSIGNED_ID()));
        bug.setCREATED_ID(null);

        return bug;
    }

    public static BugDTO getBugDTOWithoutUserAssigned(Bug bug) {
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getID());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity().toUpperCase());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setVersion(bug.getVersion());

        bugDTO.setASSIGNED_ID(null);
        bugDTO.setCREATED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getCREATED_ID()));

        return bugDTO;
    }
}
