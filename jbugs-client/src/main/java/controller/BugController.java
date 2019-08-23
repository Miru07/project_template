package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;
import utils.TokenService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * REST Controller for Bug-Attachment manipulation.
 */
@Path("/bugs")
public class BugController extends HttpServlet {

    @EJB
    private BugManagerRemote bugManagerRemote;
    @EJB
    private AttachmentManagerRemote attachmentManagerRemote;
    @EJB
    private UserManagerRemote userManagerRemote;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBugs() throws JsonProcessingException {

        List<BugDTO> bugDTOList = bugManagerRemote.getAllBugs();

        ObjectMapper jsonTransformer = new ObjectMapper();
        String listOfBugsJSON = jsonTransformer.writeValueAsString(bugDTOList);
        System.out.println(listOfBugsJSON);
        return listOfBugsJSON;
    }


    /**
     * The Controller consumes a JSON and maps its content to the BugAttachmentWrapperDTO Object
     * The BugDTO object is persisted first, so that the JPA assigns an ID to it.
     * The newly persisted BugDTO object will be assigned to the AttachmentDTO object.
     * The AttachmentDTO object will be persisted second.
     *
     * @param bugAttachmentWrapperDTO is an {@link BugAttachmentWrapperDTO} object that contains
     *                                the data for persisting a Bug and Attachment object.
     * @return true if the wrapper is persisted; false if not
     * @author Sebastian Maier
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createBug(@NotNull BugAttachmentWrapperDTO bugAttachmentWrapperDTO) {
        BugDTO bugToInsert = bugAttachmentWrapperDTO.getBug();
        AttachmentDTO attachmentToInsert = bugAttachmentWrapperDTO.getAttachment();

        String token = bugAttachmentWrapperDTO.getToken();
        Integer id = TokenService.getCurrentUserID(token);
        UserDTO createdUserDTO = userManagerRemote.findUser(id);
        bugToInsert.setCREATED_ID(createdUserDTO);

        BugDTO persistedBugWithID = bugManagerRemote.insertBug(bugToInsert);
        attachmentToInsert.setBugID(persistedBugWithID);
        AttachmentDTO persistedAttachmentWithID = attachmentManagerRemote.insertAttachment(attachmentToInsert);

        return ((persistedAttachmentWithID != null) && (persistedBugWithID != null));
    }
}
