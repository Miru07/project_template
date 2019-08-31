package controller;

import authorization.util.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.*;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    /**
     * @return a JSON with all the {@link BugDTO} objects
     * @throws JsonProcessingException if there was a problem at parsing JSON
     * @author Miruna Dinu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBugs() throws JsonProcessingException {

        BugViewDTO bugViewDTO = bugManagerRemote.getBugViewDTO();

        ObjectMapper jsonTransformer = new ObjectMapper();
        String bugViewJSON = jsonTransformer.writeValueAsString(bugViewDTO);
        System.out.println(bugViewJSON);

        return bugViewJSON;
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBugById(@PathParam("id") Integer id) throws JsonProcessingException {
        BugDTO bugDTO = bugManagerRemote.getBugById(id);
        ObjectMapper jsonTransformer = new ObjectMapper();
        String bugsJSON = jsonTransformer.writeValueAsString(bugDTO);
        System.out.println(bugsJSON);
        return bugsJSON;
    }


    /**
     * The Controller consumes a JSON and maps its content to the BugAttachmentWrapperDTO Object.
     * We pass it to the {@link BugManagerRemote} interface to persist the data.
     *
     * @param bugAttachmentWrapperDTO is an {@link BugAttachmentWrapperDTO} object that contains
     *                                the data for persisting a Bug and Attachment object.
     * @return true if the wrapper is persisted; false if not
     * @exception {@link BusinessException} bubbles up to here from {@link BugManagerRemote}.
     * If it catches this exception, we return an ERROR response to the client.
     * @author Sebastian Maier
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBug(BugAttachmentWrapperDTO bugAttachmentWrapperDTO) {
        try {
            Integer createdID = TokenService.getCurrentUserID(bugAttachmentWrapperDTO.getToken());
            bugManagerRemote.insertBug(bugAttachmentWrapperDTO, createdID);
            return Response.status(Response.Status.OK).entity("OK").build();
        } catch (BusinessException ex) {
            return Response.status(Response.Status.OK).entity("ERROR").build();
        }
    }

    /**
     * The function consumes a JSON to map it to a {@link BugDTOWrapper} object.
     * The wrapper contains a token, which we map it to an ID. We also extract the bug to be updated.
     * We pass these objects forth to the manager.
     *
     * @param bugID      is an {@link Integer} of the Bug to be updated.
     * @param wrapperDTO is an {@link BugDTOWrapper} that contains the updated details of the Bug and
     *                   the token.
     * @return {@link Response} OK, if no isses were encountered, ERROR else.
     */
    @PUT
    @Path("/update-bug/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBug(@PathParam("id") Integer bugID, BugDTOWrapper wrapperDTO) {
        try {
            Integer requestUserID = TokenService.getCurrentUserID(wrapperDTO.getToken());
            BugDTO bugToUpdate = wrapperDTO.getBugDTO();
            bugManagerRemote.updateBug(requestUserID, bugID, bugToUpdate);
            return Response.status(Response.Status.OK).entity("OK").build();
        } catch (BusinessException ex) {
            return Response.status(Response.Status.OK).entity("ERROR").build();
        }
    }

    /**
     * The controller receives a path parameter {@link Integer} that represents the bugID of the {@link BugDTO}
     * object to be closed.
     *
     * @param bugID is the id of the {@link BugDTO} object to be closed
     * @return true if the update is successful
     * @exception {@link BusinessException} if there was an error in {@link BugManagerRemote}
     * If it catches the exception, the client will receive an ERROR response.
     * @author Miruna Dinu
     */
    @PUT
    @Path("/close-bug/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response closeBug(@PathParam("id") Integer bugID){

        String message = "OK";

        try{
            BugDTO closeBugResult = bugManagerRemote.closeBug(bugID);
            return Response.status(Response.Status.OK).entity(message).build();
        } catch (BusinessException e){
            return Response.status(500).entity(e).build();
        }
    }

    @GET
    @Path("/get-att")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAtt() throws JsonProcessingException {

        List<AttachmentDTO> attachmentDTOList = attachmentManagerRemote.getAllAtt();

        ObjectMapper jsonTransformer = new ObjectMapper();
        String attachmentDTOString = jsonTransformer.writeValueAsString(attachmentDTOList);
        System.out.println(attachmentDTOString);

        return attachmentDTOString;
    }
}
