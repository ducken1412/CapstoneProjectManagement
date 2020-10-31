package com.fpt.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendingMail {

    @Resource
    private JavaMailSender javaMailSender;

    private static  SendingMail sendingMail;

    @PostConstruct
    public  void  init(){
        sendingMail = this;
        sendingMail.javaMailSender = this.javaMailSender;
    }

    public static void sendEmail(String sendTo, String subject,String content) throws MessagingException {

        MimeMessage msg = sendingMail.javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(sendTo);
        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");
        // true = text/html
        helper.setText(content, true);
        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));
        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
        sendingMail.javaMailSender.send(msg);
    }
}
