package com.base.utils.mailUtil;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2014/10/13.
 * 邮件发送工具类,只需填入发送人和发送内容
 */
@Component("mailUtils")
public class MailUtils {

    /**关于mail发送的参数*/
    @Value("${MAIL.HOSTNAME}")
    public String hostName;//服务商url
    @Value("${MAIL.PORT}")
    public String port;//端口
    @Value("${MAIL.USERNAME}")
    public String userName;
    @Value("${MAIL.PASSWORD}")
    public String userPassword;
    @Value("${MAIL.FROM}")
    public String mailFrom;

    public void sendMail(Email email) throws Exception{
        //Email email = new SimpleEmail();
        email.setHostName(hostName);
        email.setSmtpPort(Integer.valueOf(port) );
        email.setAuthenticator(new DefaultAuthenticator(userName, userPassword));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom(mailFrom);
        email.send();
    }
}
