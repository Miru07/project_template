package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
