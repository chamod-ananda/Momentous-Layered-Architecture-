package com.example.momentous.momentous_finalproject.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailController {

    @FXML
    private TextArea bodyTxtArea;

    @FXML
    private AnchorPane mailPane;

    @FXML
    private JFXButton sendButton;

    @FXML
    private TextField subjectTxtField;

    @Setter
    private String customerEmail;

    @FXML
    void sendButtonOnAction(ActionEvent event) {
        if (customerEmail == null) {
            return;
        }

        String subject = subjectTxtField.getText();
        String body = bodyTxtArea.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty").show();
            return;
        }

        sendEmail(customerEmail, subject, body);
    }

    void sendEmail(String to, String subject, String messageBody) {
        final String FROM = "chamodananda9@gmail.com";
        String PASSWORD = "oniiuawthzurxvhh";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(FROM, PASSWORD);
           }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageBody);
            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully...").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to send email...").show();
        }
    }
}
