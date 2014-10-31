package com.base.utils.mailUtil;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


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
        /*Email email1 = new SimpleEmail();
        email1.setHostName("smtp.126.com");
        email1.setSmtpPort(Integer.valueOf("465"));
        email1.setAuthenticator(new DefaultAuthenticator("byuniversal99@126.com", "byby12345"));
        email1.setSSLOnConnect(true);
        email1.setCharset("UTF-8");
        email1.setFrom("byuniversal99@126.com");
        email1.addTo("892129701@qq.com");
        email1.setSubject("tembin密码修改验证码叫姐姐");
        email1.setMsg("您正在进行密码找回操作，本次操作验证码为:");
        email1.send();*/
        email.setHostName(hostName);
        email.setSmtpPort(Integer.valueOf(port) );
        email.setAuthenticator(new DefaultAuthenticator(userName, userPassword));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom(mailFrom);
        email.send();
    }

    /*public static void main(String[] args) throws Exception {
        MailUtils mailUtils = new MailUtils();

        mailUtils.sendMail2(null);
    }


    public void sendMail2(Email email) throws Exception {
        if(email==null){
            email = new HtmlEmail();

        }
        email.setHostName("smtp.exmail.qq.com");
        email.setSmtpPort(Integer.valueOf("465"));
        // email.setAuthenticator(null);
        email.setAuthenticator(new DefaultAuthenticator("info@tembin.com", "QWQW123"));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom("info@tembin.com");
        email.addTo("892129701@qq.com");
        email.setSubject("ddd");
        email.setMsg("<a href=\"http://www.baidu.com\"><img src=\"http://www.baidu.com/img/baidu_jgylogo3.gif\"/></a>");
        email.send();
    }*/


}

