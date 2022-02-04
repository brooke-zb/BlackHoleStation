package com.brookezb.bhs.service;

/**
 * @author brooke_zb
 */
public interface MailService {
    void sendReplyMail(String to, String nickname, String link);

    void sendAuditMail(String link);
}
