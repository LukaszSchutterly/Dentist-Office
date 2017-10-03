package dentist.office.events;

import dentist.office.service.task.DaySchemeCreatingTask;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private DaySchemeCreatingTask daySchemeCreatingTask;

    public ApplicationStartup(DaySchemeCreatingTask daySchemeCreatingTask) {
        this.daySchemeCreatingTask = daySchemeCreatingTask;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        daySchemeCreatingTask.createDaySchemesForFuture();
        return;
    }

}