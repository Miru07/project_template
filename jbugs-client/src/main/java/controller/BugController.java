package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public BugAttachmentWrapperDTO createBug(BugAttachmentWrapperDTO wrapperDTO) {
        System.out.println(wrapperDTO.toString());

        bugManagerRemote.insertBug(wrapperDTO);

        return wrapperDTO;
    }
}
