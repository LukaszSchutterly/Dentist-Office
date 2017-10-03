package dentist.office.service.task;

import dentist.office.model.entity.day.DayScheme;
import dentist.office.service.entity.day.DaySchemeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaySchemeDestroyingTask {

    private DaySchemeService daySchemeService;

    public DaySchemeDestroyingTask(DaySchemeService daySchemeService) {
        this.daySchemeService = daySchemeService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void destroyPastDaySchemes() {
        List<DayScheme> daySchemesToDestroy = daySchemeService.getPastDaySchemes();

        daySchemesToDestroy.forEach(d -> {
            d.setAvailableVisitTimes(null);
            daySchemeService.removeById(d.getDate());
        });

    }

}
