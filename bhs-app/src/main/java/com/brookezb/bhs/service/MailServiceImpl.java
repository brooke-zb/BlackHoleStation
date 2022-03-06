package com.brookezb.bhs.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import com.brookezb.bhs.constant.MailConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.brookezb.bhs.utils.MailUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brooke_zb
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {
    private final MailUtils mailUtils;
    private final String template;
    private final String[] templateKeyword = {"${title}", "${content}", "${link}", "${admin}"};
    private final String adminMail;

    public MailServiceImpl(MailUtils mailUtils, @Value("${spring.mail.properties.mail.admin}") String admin) throws IOException {
        this.mailUtils = mailUtils;
        ClassPathResource resource = new ClassPathResource("/templates/mail.html");
        this.template = IoUtil.readUtf8(resource.getInputStream());
        this.adminMail = admin;
    }

    @Async("mailExecutor")
    @Override
    public void sendReplyMail(String to, String nickname, String link) {
        String cleanNickname = nickname.replace("<", "&lt;").replace(">", "&gt;");
        Map<String, String> map = Map.of(
                templateKeyword[0], MailConstants.Reply.TITLE,
                templateKeyword[1], String.format(MailConstants.Reply.CONTENT, cleanNickname),
                templateKeyword[2], link,
                templateKeyword[3], adminMail
        );

        mailUtils.sendMimeMail(to, "[黑洞空间站]回复通知", parseTemplate(map));
    }

    @Async("mailExecutor")
    @Override
    public void sendAuditMail(String link) {
        Map<String, String> map = Map.of(
                templateKeyword[0], MailConstants.Audit.TITLE,
                templateKeyword[1], MailConstants.Audit.CONTENT,
                templateKeyword[2], link,
                templateKeyword[3], adminMail
        );

        mailUtils.sendMimeMail(adminMail, "[黑洞空间站]审核通知", parseTemplate(map));
    }

    private String parseTemplate(Map<String, String> templateMap) {
        // 模板内容替换
        StringBuilder builder = new StringBuilder(template);
        for (var keyword : templateKeyword) {
            int index = builder.indexOf(keyword);
            if (index != -1) {
                builder.replace(index, index + keyword.length(), templateMap.get(keyword));
            }
        }

        return builder.toString();
    }
}
