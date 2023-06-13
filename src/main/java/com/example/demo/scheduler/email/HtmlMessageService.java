package com.example.demo.scheduler.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class HtmlMessageService {
    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String toAddress, String locationName, String timeHours, String timeYear, String message) throws MessagingException, IOException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        String beautifulTime = timeHours + "<br>" + timeYear;

        String htmlTemplate = new String(Files.readAllBytes(Paths.get("src\\main\\java\\com\\example\\demo\\scheduler\\email\\Email.html")));
        String emailContent = htmlTemplate.replace("%MESSAGE%", message).
                replace("%LOCATION%", locationName).
                replace("%TIME%", beautifulTime);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(emailContent, true);
        helper.setTo(toAddress);
        helper.setSubject("Климат вне нормы: " + locationName);
        emailSender.send(mimeMessage);
    }
}
