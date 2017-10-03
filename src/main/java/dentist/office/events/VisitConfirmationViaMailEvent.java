package dentist.office.events;

import dentist.office.model.entity.visit.Visit;
import org.springframework.context.ApplicationEvent;

public class VisitConfirmationViaMailEvent extends ApplicationEvent {

    private Visit visit;

    public VisitConfirmationViaMailEvent(Visit visit) {
        super(visit);
        this.visit = visit;
    }

    public Visit getVisit() {
        return visit;
    }


}
