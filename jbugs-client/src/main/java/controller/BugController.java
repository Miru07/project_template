package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import utils.TokenService;

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
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("ERROR").build();
        }
    }

    @PUT
    @Path("/update-bug-status/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateBugStatus(@PathParam("id") Integer bugID, String newStatus) {

        try {
            BugDTO updateResult = bugManagerRemote.updateBugStatus(newStatus, bugID);
            return Response.status(Response.Status.OK).entity(updateResult).build();
        } catch (BusinessException e) {
            return Response.status(500).entity(e.getErrorCode()).build();
        }
    }
}
