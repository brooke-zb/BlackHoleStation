package top.brookezb.bhs.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.exception.InvalidException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author brooke_zb
 */
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("黑洞空间站<noreply@brooke-zb.top>");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new InvalidException("邮件发送失败");
        }
    }
}
