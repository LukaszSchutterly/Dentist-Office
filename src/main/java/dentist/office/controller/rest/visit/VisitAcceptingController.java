package dentist.office.controller.rest.visit;

import dentist.office.model.entity.visit.Visit;
import dentist.office.service.entity.visit.VisitService;
import dentist.office.service.mail.MailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visits")
public class VisitAcceptingController {

    private VisitService visitService;
    private MailSendingService mailSendingService;

    @Autowired
    public VisitAcceptingController(VisitService visitService, MailSendingService mailSendingService) {
        this.visitService = visitService;
        this.mailSendingService = mailSendingService;
    }

    @PutMapping(value = "/acceptOrReject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void acceptOrRejectVisit(@RequestBody Visit visit) {

        if (visit.isAccepted()) {
            visitService.saveOrUpdate(visit);
            mailSendingService.sendVisitAcceptedEmail(visit);
        } else {
            visitService.removeById(visit.getId());
            mailSendingService.sendVisitRejectedEmail(visit);
        }

    }

}
