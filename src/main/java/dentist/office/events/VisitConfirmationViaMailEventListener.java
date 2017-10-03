package dentist.office.events;

import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.service.entity.token.VisitConfirmationTokenService;
import dentist.office.service.mail.MailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class VisitConfirmationViaMailEventListener implements ApplicationListener<VisitConfirmationViaMailEvent> {

    private MailSendingService mailSendingService;
    private VisitConfirmationTokenService visitTokenService;

    @Autowired
    public VisitConfirmationViaMailEventListener(MailSendingService mailSendingService, VisitConfirmationTokenService visitTokenService) {
        this.mailSendingService = mailSendingService;
        this.visitTokenService = visitTokenService;
    }

    @Override
    public void onApplicationEvent(VisitConfirmationViaMailEvent event) {
        VisitConfirmationToken visitConfirmationToken = new VisitConfirmationToken(event.getVisit());
        visitTokenService.saveOrUpdate(visitConfirmationToken);

        mailSendingService.sendConfirmationEmail(visitConfirmationToken);
    }

}
