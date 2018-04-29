package si.fri.tpo.team7.services.beans.management;

import si.fri.tpo.team7.entities.users.User;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class ManagementBean {

    private Logger log = Logger.getLogger(ManagementBean.class.getName());

    @PersistenceContext(unitName = "studis-jpa")
    private EntityManager em;

    @Transactional
    public void sendMail(String to) throws MessagingException {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.eMail = :to");
        query.setParameter("to", to);
        User u = (User) query.getSingleResult();
        u.setPasswordResetToken();



        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;


        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("zs7373@student.uni-lj.si"));
        generateMailMessage.setFrom(new InternetAddress("team7.studis@gmail.com"));
        generateMailMessage.setSubject("Pozabljeno geslo - STUDIS");
        String emailBody = "Pozdravljeni, " + "<br><br> zahtevali ste novo geslo. To lahko storite tukaj: <br>" + "http://localhost:8081/token/password/set/" + u.getPasswordResetToken();
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "team7.studis", "hzQJNif56pa5");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();

    }

    @Transactional
    public void  resetPassword(String token, String newPassword){
        Query query = em.createQuery("SELECT u FROM User u WHERE u.passwordResetToken = :token");
        query.setParameter("token", token);
        User u = (User) query.getSingleResult();
        u.setPassword(newPassword);
    }
}
