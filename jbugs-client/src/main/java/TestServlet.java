import ro.msg.edu.jbugs.MailSender;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserBugsDTO;
import ro.msg.edu.jbugs.exceptions.BusinessException;
import ro.msg.edu.jbugs.manager.remote.BugManagerRemote;
import ro.msg.edu.jbugs.manager.impl.CommentManager;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.manager.impl.UserManager;
import ro.msg.edu.jbugs.manager.remote.CommentManagerRemote;
import ro.msg.edu.jbugs.manager.remote.NotificationManagerRemote;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;


import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
        message = "Hello, M.";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

//        String text = request.getParameter("text") != null ? request.getParameter("text") : "Hello World";
//
//        try{
//            Context ic = new InitialContext();
//            ConnectionFactory cf = (ConnectionFactory) ic.lookup("java:comp/DefaultJMSConnectionFactory");
//            Queue queue = (Queue) ic.lookup("tutorialQueue");
//            Connection connection = cf.createConnection();
//
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageProducer publisher = session.createProducer(queue);
//            connection.start();
//            TextMessage message = session.createTextMessage(text);
//            publisher.send(message);
//
//        } catch (NamingException | JMSException e) {
//            response.getWriter().println("Error while trying to send <" + text + "> message: " + e.getMessage());
//        }
//        response.getWriter().println("Message sent: " + text);



        insertUser(0, "test5", "test5", "test5", "0123456", "test5", "test5", 1);

        insertUser(0, "test6", "test6", "test6", "0123456", "test6", "test6", 1);

        UserDTO userDTO = userManager.findUser(1);
//        List<UserDTO> userDTOList = userManager.findAllUsers();
//
//        for(UserDTO userDTO : userDTOList){
//
//
//            out.println("<br>" + userDTO.toString() + "<br>");
//        }

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

        userDTO = new UserDTO(counter, fName, lName, phoneNr, email, username, password, status);
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
