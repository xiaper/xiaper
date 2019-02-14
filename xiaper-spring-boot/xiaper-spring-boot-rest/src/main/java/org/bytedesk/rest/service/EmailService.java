package org.bytedesk.rest.service;

import org.bytedesk.jpa.constant.BdConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 发送邮件服务
 *
 * 参考：
 * https://www.thymeleaf.org/doc/articles/springmail.html
 * http://blog.didispace.com/springbootmailsender/
 *
 * TODO：跟推送服务，一起迁移到单独服务器
 *
 * @author bytedesk.com
 */
@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.self.username}")
    private String from;

    /**
     * 发送简单文本邮件
     *
     * 经测试，可以发送成功
     */
    public void sendSimpleMail(String toMail, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();

        // from 需要跟配置文件中的授权用户名保持一致，否则报错
        message.setFrom(from);
        // 收件人email
        message.setTo(toMail);
        message.setSubject(subject);
        //
        message.setText(content);

        mailSender.send(message);
    }


    /**
     * 发送html邮件
     *
     * 经测试，可以发送成功
     * TODO: 防止重复发送机制，60秒内仅能够发送1次
     *
     * @param email
     * @param code
     */
    public void sendRegisterMail(String email,  int code) {

        // 构造模板引擎
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        // 模板所在目录，相对于当前classloader的classpath。
        resolver.setPrefix("templates/email/");
        // 模板文件后缀
        resolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(true);
        //
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        // 设置变量
        final Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("code", code);

        //
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            // true 表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(new InternetAddress(BdConstants.PROJECT_NAME + " <" + from + ">"));
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("注册验证码");

            // Create the HTML body using Thymeleaf
            String htmlContent = templateEngine.process("register.html", context);
            // true = isHtml
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }


    /**
     * 发送带有附件的邮件
     *
     * @throws Exception
     */
    public void sendAttachmentsMail(String toMail, String subject, String content) throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(from);
        helper.setTo(toMail);
        helper.setSubject(subject);

        helper.setText(content);

        FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
        helper.addAttachment("附件-1.jpg", file);
        helper.addAttachment("附件-2.jpg", file);

        mailSender.send(mimeMessage);
    }


    /**
     * 嵌入静态资源
     *
     * @throws Exception
     */
    public void sendInlineMail(String toMail, String subject, String content) throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(from);
        helper.setTo(toMail);
        helper.setSubject(subject);

        helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);

        FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
        helper.addInline("weixin", file);

        mailSender.send(mimeMessage);
    }






}




