package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.BugViewDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import utils.TokenService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * REST Controller for Bug-Attachment manipulation.
 */
@Path("/bugs")
public class BugController extends HttpServlet {

    @EJB
    private BugManagerRemote bugManagerRemote;

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
     * The controller receives a path parameter {@link Integer} that represents the bugId of the
     * {@link BugDTO} object whose status is to
     * be changed. It also consumes a text and maps it to a String.
     * We pass it to the {@link BugManagerRemote} interface to update the data.
     *
     * @param bugID is the id of the {@link BugDTO} object whose status is to be changed
     * @param newStatus is the new status of the {@link BugDTO} object
     * @return true if the update is successful
     * @exception {@link BusinessException} if there was an error in {@link BugManagerRemote}
     * If it catches the exception, the client will receive an ERROR response.
     * @author Miruna Dinu
     */
    @PUT
    @Path("/update-bug-status/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateBugStatus(@PathParam("id") Integer bugID, String newStatus) {


        try {
            BugDTO updateResult = bugManagerRemote.updateBugStatus(newStatus, bugID);
            return Response.status(Response.Status.OK).entity("OK").build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e).build();
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response closeBug(@PathParam("id") Integer bugID){

        try{
            BugDTO closeBugResult = bugManagerRemote.closeBug(bugID);
            return Response.status(Response.Status.OK).entity("OK").build();
        } catch (BusinessException e){
            return Response.status(500).entity(e).build();
        }
    }
}
