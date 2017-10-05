package dentist.office.service.mail;

import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.model.entity.visit.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSendingService {

    private JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendMimeMessage(MimeMessage mimeMessage, String content, String Subject, String emailAddress) {

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessage.setContent(content,"text/html; charset=UTF-8");
            mimeMessageHelper.setTo(emailAddress);
            mimeMessageHelper.setSubject(Subject);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void sendConfirmationEmail(VisitConfirmationToken visitConfirmationToken) {
        Visit visit = visitConfirmationToken.getVisit();

        String token = visitConfirmationToken.getToken();
        String emailAddress = visit.getPatient().getEmail();
        String subject = "Rejestracja";
        String content = "<p>Twój kod do rejestracji wizyty to: <br/>" +
                "<strong>" + token + "</strong><br/>" +
                " Wiadmość wygeneraowana automatycznie</p>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        sendMimeMessage(mimeMessage, content, subject, emailAddress);
    }

    public void sendVisitAcceptedEmail(Visit visit) {
        Patient patient = visit.getPatient();
        String emailAddress = patient.getEmail();
        String subject = "Twoja wizyta została zaakceptowana";
        String content = "<h2>Witaj " + patient.getFirstName() + "</h2>" +
                "<p>Twoja wizyta została zaakceptowa. Termin wizyty to: <br/>" +
                "<strong>" + visit.getVisitDate() + "<br/>"
                + visit.getVisitTime() + "</strong><br/>" +
                "Jeżeli z jakiegoś powodu nie możesz przyjść na wizytyę prosimy o informacje na nr. tel 3131231233" +
                " Wiadmosść wygeneraowana automatycznie</p>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        sendMimeMessage(mimeMessage, content, subject, emailAddress);
    }

    public void sendVisitRejectedEmail(Visit visit) {
        Patient patient = visit.getPatient();
        String emailAddress = patient.getEmail();
        String subject = "Twoja wizyta nie została zaakceptowana";
        String content = "<h2>Witaj " + patient.getFirstName() + "</h2>" +
                "<p> Niestety okazało sie ze wybrany przez ciebie termin nie został zaakceptowany." +
                "Spróboj zarejestrować się ponownie lub zrób to dzwoniąc pod nr. tel 123123123" +
                " Wiadmosść wygeneraowana automatycznie</p>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        sendMimeMessage(mimeMessage, content, subject, emailAddress);
    }

}
