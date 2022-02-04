package com.brookezb.bhs.utils;

import com.brookezb.bhs.exception.InvalidException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author brooke_zb
 */
@Slf4j
@Service
@AllArgsConstructor
public class MailUtils {
    private JavaMailSender mailSender;
    private MailProperties mailProperties;

    public void sendMimeMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("黑洞空间站<" + mailProperties.getUsername() + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送失败", e);
            throw new InvalidException("邮件发送失败");
        }
    }
}
