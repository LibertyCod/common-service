package com.liberty.share.util;

import com.xiaoleilu.hutool.crypto.SecureUtil;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Binbin Wang
 * @date 2018/3/9
 */
public class EmailUtil {

    private final static Map<String, Object> EMAIL_INFO = new HashMap<>();

    private static String token;
    static {
        Environment properties = ApplicationContextUtil.getApplicationContext().getBean(Environment.class);
        EMAIL_INFO.put("email.host", properties.getProperty("email.host"));
        EMAIL_INFO.put("email.from", properties.getProperty("email.from"));
        EMAIL_INFO.put("email.password", properties.getProperty("email.password"));
        token = properties.getProperty("udesk.token");
    }

    /**
     * 发送邮件工具类
     * @param to
     * @param subject
     * @param emailContent
     * @param isHtml
     * @throws Exception
     */
    public static void sendHtmlMail(String to, String subject, String emailContent, boolean isHtml)throws Exception {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        //设定mail server
        senderImpl.setHost((String) EMAIL_INFO.get("email.host"));

        //建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, false, "UTF-8");
        //设置收件人，寄件人
        messageHelper.setTo(to);
        messageHelper.setFrom((String) EMAIL_INFO.get("email.from"));
        messageHelper.setSubject(subject);
        //true 表示启动HTML格式的邮件
        messageHelper.setText(emailContent, isHtml);
        //设置邮箱属性
        Properties prop = new Properties() ;
        // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.auth", "true") ;
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", EMAIL_INFO.get("email.host"));
        prop.put("mail.smtp.user", EMAIL_INFO.get("email.from"));
        prop.put("mail.smtp.pass", EMAIL_INFO.get("email.password"));
        //配置发送类信息
        senderImpl.setJavaMailProperties(prop);
        senderImpl.setUsername((String) EMAIL_INFO.get("email.from"));
        senderImpl.setPassword((String) EMAIL_INFO.get("email.password"));
        senderImpl.setSession(Session.getDefaultInstance(senderImpl.getJavaMailProperties()));
        //发送邮件
        senderImpl.send(mailMessage);
    }

    public static String getSign(String email, Long timestamp) {
        StringBuilder origin = new StringBuilder();
        origin.append(email)
                .append("&")
                .append(token)
                .append("&")
                .append(timestamp);
        return SecureUtil.sha1(origin.toString());
    }

}
