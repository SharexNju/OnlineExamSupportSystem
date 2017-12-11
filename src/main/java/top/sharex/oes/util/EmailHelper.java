package top.sharex.oes.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sharex.oes.config.EmailConfig;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author danielyang
 * @Date 2017/10/30 17:13
 */
@Component
public class EmailHelper {
    @Autowired
    private EmailConfig emailConfig;

    private Session session;

    @PostConstruct
    void init() {
        Properties props = new Properties();
//        // 开启debug调试
//        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", emailConfig.getHostAddress());
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        // 设置以ssh加密传输
        props.put("mail.smtp.ssl.enable", "true");

        session = Session.getInstance(props);
    }

    public boolean sendEmail(String toUser, String subject, String text) throws MessagingException {

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        // 设置邮件内容
        msg.setText(text);
        // 设置发件人
        msg.setFrom(new InternetAddress(emailConfig.getSenderAddress()));

        Transport transport = session.getTransport();
        // 连接邮件服务器，qq邮箱需要开启授权码并在密码处填入授权码
        transport.connect(emailConfig.getUsername(), emailConfig.getPassword());
        // 发送邮件
        transport.sendMessage(msg, new Address[]{new InternetAddress(toUser)});
        // 关闭连接
        transport.close();
        return true;
    }
}
