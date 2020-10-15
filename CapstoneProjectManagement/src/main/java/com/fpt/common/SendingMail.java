package com.fpt.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SendingMail {


    private JavaMailSender javaMailSender;

    public  void sendEmailWithAttachment(String sendTo, String subject,String content,String path) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

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
        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
    }
}
