package ro.msg.edu.jbugs.dtoEntityMapper;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.entity.Bug;

public class BugDTOEntityMapper {

    private BugDTOEntityMapper(){

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
        bug.setCREATED_ID(UserDTOEntityMapper.getUserFromUserDTO(bugDTO.getCREATED_ID()));


        return bug;
    }
    public static BugDTO getBugDTO(Bug bug){
        BugDTO bugDTO = new BugDTO();
        bugDTO.setID(bug.getID());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setFixedVersion(bug.getFixedVersion());

        bugDTO.setASSIGNED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getASSIGNED_ID()));
        bugDTO.setCREATED_ID(UserDTOEntityMapper.getDTOFromUser(bug.getCREATED_ID()));

        return bugDTO;
    }

}
