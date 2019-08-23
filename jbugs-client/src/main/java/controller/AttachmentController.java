package controller;

import ro.msg.edu.jbugs.manager.remote.AttachmentManagerRemote;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/attachments")
public class AttachmentController extends HttpServlet {
    @EJB
    private AttachmentManagerRemote attachmentManager;

//    @POST
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
//    @Produces(MediaType.APPLICATION_JSON)
//    public AttachmentDTO createAttachment(
//            @FormDataParam("attContent") InputStream fileInputStream,
//            @FormDataParam("attContent") FormDataContentDisposition fileMetaData,
//            @FormDataParam("ID") int id,
//            @FormDataParam("bugID") BugDTO bugDTO) throws Exception
//    {
//        try{
//            System.out.println("AICI TREBUIE SA FIE CEVA PLS WORK OMG");
//            //System.out.println(attachmentDTO.toString());
//
//            int read = 0;
//            byte[] bytes = new byte[1024];
//            File file = new File(fileMetaData.getFileName());
//            System.out.println("Upload File Path : "+file.getAbsolutePath());
//            OutputStream out = new FileOutputStream(file);
//            while ((read = fileInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//            out.flush();
//            out.close();
//
//            System.out.println(bytes);
//            //attachmentDTO.setAttContent(bytes);
//
//            AttachmentDTO toInsert = new AttachmentDTO(id, bytes, bugDTO);
//
//            return attachmentManager.insertAttachment(toInsert);
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean insertAttachment() {
        return true;
    }
}
