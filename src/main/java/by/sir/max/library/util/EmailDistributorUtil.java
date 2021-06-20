package by.sir.max.library.util;

import by.sir.max.library.exception.UtilException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailDistributorUtil {
    private static final Logger LOGGER = LogManager.getLogger(EmailDistributorUtil.class);

    private static final int DEFAULT_DELAY_TO_SEND_EMAILS = 1;

    private static final String ENCODING_UTF_8 = "utf-8";
    private static final String PROPERTIES_FILE = "email.properties";
    private static final String HOST_PROPERTY_NAME = "mail.smtp.host";
    private static final String FROM_PROPERTY_NAME = "mail.smtp.user";
    private static final String PASSWORD_PROPERTY_NAME = "mail.smtp.password";
    
    private final String host;
    private final String from;
    private final String password;
    private final Session mailSession;
    private final CopyOnWriteArrayList<Email> emailList;
     
    public EmailDistributorUtil(){
        Properties mailProperties = new Properties();
        try {
            mailProperties.load(EmailDistributorUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.fatal("Can't initialize mail properties", e);
            throw new RuntimeException("Can't initialize mail properties", e);
        }
        emailList = new CopyOnWriteArrayList<>();
        mailSession = Session.getDefaultInstance(mailProperties);
        host = mailProperties.getProperty(HOST_PROPERTY_NAME);
        from = mailProperties.getProperty(FROM_PROPERTY_NAME);
        password = mailProperties.getProperty(PASSWORD_PROPERTY_NAME);
        initEmailDistributorThread();
    }

    private void initEmailDistributorThread() {
        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread singleDaemonThread = new Thread(runnable);
                singleDaemonThread.setDaemon(true);
                return singleDaemonThread;
            }
        }).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendEmailsIfExist();
            }
        }, DEFAULT_DELAY_TO_SEND_EMAILS, DEFAULT_DELAY_TO_SEND_EMAILS, TimeUnit.MINUTES);
    }

    public void addEmailToSendingQueue(String subject, String text, String destinationEmail) throws UtilException {
        if (StringUtils.isAnyBlank(subject, text, destinationEmail)) {
            throw new UtilException("Invalid email param");
        }
        emailList.add(new Email(subject, text, destinationEmail));
    }

    private void sendEmail(Email email) {
        try (Transport transport = mailSession.getTransport()) {
            MimeMessage letter = new MimeMessage(mailSession);
            letter.setFrom(new InternetAddress(from));
            letter.addRecipient(Message.RecipientType.TO, new InternetAddress(email.destinationEmail));
            letter.setSubject(email.subject, ENCODING_UTF_8);
            letter.setText(email.message, ENCODING_UTF_8);
            transport.connect(host, from, password);
            transport.sendMessage(letter, letter.getAllRecipients());
        } catch (MessagingException e) {
            LOGGER.warn(String.format("Can't send message to %s", email.destinationEmail), e);
        }
    }

    public void sendEmailsIfExist() {
        if (!emailList.isEmpty()) {
            for (Email email : emailList) {
                sendEmail(email);
            }
            emailList.clear();
        }
    }

    private static class Email {
        String subject;
        String message;
        String destinationEmail;

        Email(String subject, String message, String destinationEmail) {
            this.subject = subject;
            this.message = message;
            this.destinationEmail = destinationEmail;
        }
    }
}
