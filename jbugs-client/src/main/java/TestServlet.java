import ro.msg.edu.jbugs.MailSender;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserBugsDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import ro.msg.edu.jbugs.manager.remote.CommentManagerRemote;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {

    private String message;
    private UserDTO userDTO;
    @EJB
    private UserManagerRemote userManager;
    @EJB
    private BugManagerRemote bugManager;
    @EJB
    private CommentManagerRemote commentManager;
    @EJB
    private NotificationManagerRemote notificationManager;

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            UserDTO userDTO1 = userManager.login("test5t", "test5");
            out.println("Hello, " + userDTO1.getUsername());
        } catch (BusinessException e){
            e.printStackTrace();
        }
    }


    public void sendMail(HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        Integer deleteResult = commentManager.deleteCommentsOlderThan1Year();
        out.println("Deleted rows: " + deleteResult);

        Integer updateResult = bugManager.updateBugStatus();
        out.println("Updated rows: " + updateResult);

        MailSender.sendMail(deleteResult, updateResult);
        out.println("Check mail :) ");
    }

    public void insertUser(Integer counter, String email, String fName, String lName, String phoneNr, String password,
                           String username, Integer status){

        userDTO = new UserDTO(0, fName, lName, username, password, counter, email, phoneNr, status, null);
        userManager.insertUser(userDTO);
    }

    public void printUserBugs(HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        List<UserBugsDTO> userBugs = userManager.getUserBugs();

        for(UserBugsDTO u : userBugs){

            out.println(u.toString() + "<br>");
        }

    }

    public void printUsersCreatedByUser(HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();

        UserDTO userDTO2 = userManager.findUser(1);
        out.println(userDTO2.toString());

        List<BugDTO> bugDTOS = bugManager.findBugsCreatedBy(userDTO2);

        for(BugDTO b: bugDTOS){
            out.println(b.toString());
        }

    }


    public void destroy() {
        // do nothing.
    }
}
