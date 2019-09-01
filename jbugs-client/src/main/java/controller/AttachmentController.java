package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/attachments")
public class AttachmentController {

    @EJB
    private AttachmentManagerRemote attachmentManagerRemote;

    /**
     * @return a JSON with all the {@link AttachmentDTO} objects
     * @throws JsonProcessingException if there was a problem at parsing JSON
     * @author Miruna Dinu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAtt() throws JsonProcessingException {

        List<AttachmentDTO> attachmentDTOList = attachmentManagerRemote.getAllAtt();
        System.out.println(attachmentDTOList);

        ObjectMapper jsonTransformer = new ObjectMapper();
        String attachmentDTOString = jsonTransformer.writeValueAsString(attachmentDTOList);
        System.out.println(attachmentDTOString);

        return attachmentDTOString;
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public Response deleteAttachment(@PathParam("id") Integer attachmentID){

        String message = "OK";

        try{
            this.attachmentManagerRemote.deleteAttachment(attachmentID);
            return Response.status(Response.Status.OK).entity(message).build();
        } catch (Exception e){
            return Response.status(500).entity(e).build();
        }
    }
}
