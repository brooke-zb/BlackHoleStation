package top.brookezb.bhs.service;

/**
 * @author brooke_zb
 */
public interface MailService {
    void sendMail(String to, String subject, String content);
}
