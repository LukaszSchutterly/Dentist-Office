package dentist.office.controller.rest.visit;

import dentist.office.dto.RegistrationDTO;
import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.model.entity.visit.Visit;
import dentist.office.service.entity.token.VisitConfirmationTokenService;
import dentist.office.service.entity.visit.VisitService;
import dentist.office.service.mail.MailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class VisitConfirmationController {

    private VisitConfirmationTokenService confirmationTokenService;
    private VisitService visitService;
    private MailSendingService mailSendingService;

    @Autowired
    public VisitConfirmationController(VisitConfirmationTokenService confirmationTokenService, VisitService visitService) {
        this.confirmationTokenService = confirmationTokenService;
        this.visitService = visitService;
    }

    @Autowired
    public void setMailSendingService(MailSendingService mailSendingService) {
        this.mailSendingService = mailSendingService;
    }

    @GetMapping(value = "/confirm")
    public ResponseEntity<Boolean> confirm(@RequestParam("token") String token) {
        VisitConfirmationToken visitConfirmationToken = confirmationTokenService.getByToken(token);

        boolean confirmationTokenValid = visitConfirmationToken != null;

        if (confirmationTokenValid) {
            Visit visitToConfirm = visitConfirmationToken.getVisit();

            visitToConfirm.setConfirmed(true);
            visitService.saveOrUpdate(visitToConfirm);
            confirmationTokenService.removeById(visitConfirmationToken.getId());
        }

        return new ResponseEntity<>(confirmationTokenValid, HttpStatus.OK);
    }

    @PostMapping(value = "/resendMail")
    public ResponseEntity<Void> resend(@RequestBody RegistrationDTO registrationDTO) {
        LocalDateTime visitDateTime = registrationDTO.getVisitDateTime();

        VisitConfirmationToken visitConfirmationToken = confirmationTokenService
                .getByVisitDateAndVisitTime(visitDateTime.toLocalDate(), visitDateTime.toLocalTime());

        if (visitConfirmationToken != null) {
            mailSendingService.sendConfirmationEmail(visitConfirmationToken);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
