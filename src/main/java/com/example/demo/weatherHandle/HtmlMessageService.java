package com.example.demo.weatherHandle;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class HtmlMessageService {
    @Autowired
    public JavaMailSender emailSender;
    public void sendEmail(String toAddress, String locationName, String message) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<!DOCTYPE html><html lang=\"ru\"><body><p style=\"font-size: 24px;\">" + message + "</p></body></html>\n";
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"ru\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Letter</title>\n" +
                "    <style>\n" +
                "      .letter {\n" +
                "        width: 700px;\n" +
                "        height: 700px;\n" +
                "        margin: 0 auto;\n" +
                "        display: flex;\n" +
                "        flex-direction: column;\n" +
                "        font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif;\n" +
                "        background: linear-gradient(120deg, #2980b9, #8e44ad);\n" +
                "        align-items: center;\n" +
                "      }\n" +
                "      .letter__title {\n" +
                "        color: #fff;\n" +
                "        margin: 0;\n" +
                "        margin-top: 20px;\n" +
                "      }\n" +
                "      .letter__container {\n" +
                "        width: 600px;\n" +
                "        height: 550px;\n" +
                "        background-color: #fff;\n" +
                "        margin-top: 40px;\n" +
                "        display: flex;\n" +
                "        flex-direction: column;\n" +
                "        align-items: center;\n" +
                "        justify-content: center;\n" +
                "      }\n" +
                "      .text {\n" +
                "        margin: 0;\n" +
                "        font-size: 28px;\n" +
                "        font-weight: 500;\n" +
                "        color: #2980b9;\n" +
                "        margin-top: 40px;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div class=\"letter\">\n" +
                "      <h1 class=\"letter__title\">Климат вне нормы</h1>\n" +
                "      <div class=\"letter__container\">\n" +
                "        <p class=\"humadity text\">"+message+"</p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>";
        helper.setText(html, true);
        helper.setTo(toAddress);
        helper.setSubject("Климат вне нормы: " + locationName);
        emailSender.send(mimeMessage);
    }
}
