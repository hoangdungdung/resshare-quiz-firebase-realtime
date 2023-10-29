




import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import java.util.Date;


import java.util.Properties;
//File Name SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

//package com.sendemail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static void main(String[] args) {

        // Recipient's email ID needs to be mentioned.
        String to = "sacmauhoagiay@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "sacmauhoagiay@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("sacmauhoagiay@gmail.com",  "392068@Ss");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}

//
//public class SendMail {
//
//public static void main(String [] args) {    
//   // Recipient's email ID needs to be mentioned.
//   String to = "sacmauhoagiay@gmail.com";
//
//   // Sender's email ID needs to be mentioned
//   String from = "sacmauhoagiay@gmail.com";
//
//   // Assuming you are sending email from localhost
//   String host = "localhost";
//
//   // Get system properties
//   Properties properties = System.getProperties();
//
//   // Setup mail server
//   properties.setProperty("mail.smtp.host", host);
//
//   // Get the default Session object.
//   Session session = Session.getDefaultInstance(properties);
//
//   try {
//      // Create a default MimeMessage object.
//      MimeMessage message = new MimeMessage(session);
//
//      // Set From: header field of the header.
//      message.setFrom(new InternetAddress(from));
//
//      // Set To: header field of the header.
//      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//      // Set Subject: header field
//      message.setSubject("This is the Subject Line!");
//
//      // Now set the actual message
//      message.setText("This is actual message");
//
//      // Send message
//      Transport.send(message);
//      System.out.println("Sent message successfully....");
//   } catch (MessagingException mex) {
//      mex.printStackTrace();
//   }
//}
//}

//
//
//public class SendMail {
//
//    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
//
//        final String fromEmail = "sacmauhoagiay@gmail.com";
//        // Mat khai email cua ban
//        final String password = "392068@Ss";
//        // dia chi email nguoi nhan
//        final String toEmail = "sacmauhoagiay@gmail.com";
//
//        final String subject = "Java Example Test";
//        final String body = "Hello Admin";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//        props.put("mail.smtp.port", "587"); //TLS Port
//        props.put("mail.smtp.auth", "true"); //enable authentication
//        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//
//        Authenticator auth = new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        };
//        Session session = Session.getInstance(props, auth);
//
//
//        MimeMessage msg = new MimeMessage(session);
//        //set message headers
//        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//        msg.addHeader("format", "flowed");
//        msg.addHeader("Content-Transfer-Encoding", "8bit");
//
//        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
//
//        msg.setReplyTo(InternetAddress.parse(fromEmail, false));
//
//        msg.setSubject(subject, "UTF-8");
//
//        msg.setText(body, "UTF-8");
//
//        msg.setSentDate(new Date());
//
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//        Transport.send(msg);
//        System.out.println("Gui mail thanh cong");
//    }
//}