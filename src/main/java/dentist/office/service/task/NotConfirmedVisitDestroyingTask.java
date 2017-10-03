package dentist.office.service.task;

import dentist.office.model.entity.token.VisitConfirmationToken;
import dentist.office.model.entity.visit.Visit;
import dentist.office.service.entity.day.DaySchemeService;
import dentist.office.service.entity.token.VisitConfirmationTokenService;
import dentist.office.service.entity.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotConfirmedVisitDestroyingTask {

    private VisitConfirmationTokenService confirmationTokenService;
    private VisitService visitService;
    private DaySchemeService daySchemeService;

    @Autowired
    public NotConfirmedVisitDestroyingTask(VisitConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setDaySchemeService(DaySchemeService daySchemeService) {
        this.daySchemeService = daySchemeService;
    }

    @Scheduled(cron = "0 30 * * * *")
    public void removeExpiredTokens() {
        List<VisitConfirmationToken> expiredVisitConfirmationTokens = confirmationTokenService.getExpiredTokens();

        expiredVisitConfirmationTokens.forEach(t -> {
            Visit notConfirmedVisit = t.getVisit();

            daySchemeService.addAvailableVisitTime(notConfirmedVisit.getVisitDate(), notConfirmedVisit.getVisitTime());
            visitService.removeById(notConfirmedVisit.getId());
            confirmationTokenService.removeById(t.getId());
        });

    }

}
