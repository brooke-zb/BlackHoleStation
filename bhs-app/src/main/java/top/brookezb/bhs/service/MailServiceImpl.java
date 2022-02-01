package top.brookezb.bhs.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.URLUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import top.brookezb.bhs.utils.MailUtils;

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
    private final Map<String, String> templateMap = new HashMap<>(4);

    public MailServiceImpl(MailUtils mailUtils, @Value("${spring.mail.properties.mail.admin}") String admin) throws IOException {
        this.mailUtils = mailUtils;
        ClassPathResource resource = new ClassPathResource("/templates/mail.html");
        this.template = IoUtil.readUtf8(resource.getInputStream());
        templateMap.put(templateKeyword[3], admin);
    }

    @Override
    public void sendReplyMail(String to, String nickname, String link) {
        templateMap.put(templateKeyword[0], "回复通知");
        templateMap.put(templateKeyword[1], URLUtil.encode(nickname) + "在黑洞空间站回复了您，点击下方链接查看！");
        templateMap.put(templateKeyword[2], link);

        // 模板内容替换
        StringBuilder builder = new StringBuilder(template);
        for (var keyword : templateKeyword) {
            int index = builder.indexOf(keyword);
            if (index != -1) {
                builder.replace(index, index + keyword.length(), templateMap.get(keyword));
            }
        }

        mailUtils.sendMimeMail(to, "[黑洞空间站]回复通知", builder.toString());
    }

    @Override
    public void sendAuditMail(String link) {
        templateMap.put(templateKeyword[0], "审核通知");
        templateMap.put(templateKeyword[1], "您在黑洞空间站有新的审核，点击下方链接查看！");
        templateMap.put(templateKeyword[2], link);

        // 模板内容替换
        StringBuilder builder = new StringBuilder(template);
        for (var keyword : templateKeyword) {
            int index = builder.indexOf(keyword);
            if (index != -1) {
                builder.replace(index, index + keyword.length(), templateMap.get(keyword));
            }
        }

        mailUtils.sendMimeMail(templateMap.get("${admin}"), "[黑洞空间站]审核通知", builder.toString());
    }
}
