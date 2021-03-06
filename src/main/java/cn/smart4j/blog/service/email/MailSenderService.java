package cn.smart4j.blog.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import cn.smart4j.blog.core.Constants;
import cn.smart4j.blog.service.vo.MailVO;
import cn.smart4j.blog.web.backend.form.MailOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件发送服务
 *
 * @author zhang
 */
@Service
public class MailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    private JavaMailSenderImpl sender = new JavaMailSenderImpl();
    private boolean init = false;

    public void setServerInfo(MailOption form) {
        sender.setHost(form.getHost());
        sender.setPort(form.getPort());
        sender.setUsername(form.getUsername());
        sender.setPassword(form.getPassword());
        init = true;
    }

    /**
     * 发送邮件
     *
     * @param mail
     */
    public void sendMail(MailVO mail) {
        if (!init)
            return;

        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, Constants.ENCODING_UTF_8.name());
            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo().toArray(new String[]{}));
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent(), true);
            sender.send(message);
        } catch (MessagingException e) {
            logger.error("send mail error", e);
        }
    }

}
