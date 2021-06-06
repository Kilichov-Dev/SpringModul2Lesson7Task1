package uz.pdp.muharrir.component;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailSender {
    @Autowired
    JavaMailSender mailSender;

    public boolean send(String to, String text) throws MessagingException {
        String from = "kilichov190394@gmail.com";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Confirm email");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(text, true);
        mailSender.send(message);
        return true;
    }

    public boolean mailTextAdd(String email) throws MessagingException {
        String link = "http:localhost:8080/auth/verifyEmail?email=" + email;
        String text =
                "<a href=\"" + link + "\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">Emailni tasdiqlash</a>\n" +
                        "<br>\n" +
                        "<br>\n";
        return send(email, text);
    }
}
