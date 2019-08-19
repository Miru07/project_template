package ro.msg.edu.jbugs;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailSender {

    private MailSender(){

    }

//    @Lock(LockType.READ)
//    @Schedule(second = "*", minute = "*/5", hour="*", persistent = false)
    public static void sendMail(Integer deleteResult, Integer updateResult){

        Properties prop = System.getProperties();
        prop.put("mail.host", "smtp.gmail.com");
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.setProperty("java.net.preferIPv4Stack", "true");

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rikkidinu@gmail.com","mel67dum99");
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rikkidinu@gmail.com"));
            InternetAddress[] address = {new InternetAddress("rikkidinu@gmail.com")};
            message.setRecipients(
                    Message.RecipientType.TO, address);
            message.setSubject("Mail Subject");

            String msg = "Deleted rows: " + deleteResult + "<br> Updated rows: " + updateResult;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect();
            Transport.send(message, address);
            transport.close();
        }catch (MessagingException me){
            me.printStackTrace();
        }
    }
}
