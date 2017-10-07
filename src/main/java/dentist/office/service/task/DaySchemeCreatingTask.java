package dentist.office.service.task;

import dentist.office.model.entity.day.DayScheme;
import dentist.office.model.entity.day.DaySchemeBuilder;
import dentist.office.service.entity.day.DaySchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

@Component
public class DaySchemeCreatingTask {

    private static final int NUMBER_OF_REQUIRED_FUTURE_SCHEMES = 30;

    private DaySchemeService daySchemeService;
    private DaySchemeBuilder daySchemeBuilder;

    @Autowired
    public DaySchemeCreatingTask(DaySchemeService daySchemeService, DaySchemeBuilder daySchemeBuilder) {
        this.daySchemeService = daySchemeService;
        this.daySchemeBuilder = daySchemeBuilder;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void createDaySchemesForFuture() {
        LocalDate latestDaySchemeDate = daySchemeService.getLatestDaySchemeDate();

        if (latestDaySchemeDate == null) {
            createDaySchemes(LocalDate.now(), NUMBER_OF_REQUIRED_FUTURE_SCHEMES);
        } else {
            int numberOfDaySchemesToCreate = calculateNumberOfDaySchemesToCreate(latestDaySchemeDate);

            createDaySchemes(latestDaySchemeDate, numberOfDaySchemesToCreate);
        }
    }

    private void createDaySchemes(LocalDate latestDaySchemeDate, int howMany) {
        IntStream.rangeClosed(1, howMany).forEach(i -> {
            LocalDate dateUnderProcessing = latestDaySchemeDate.plusDays(i);

            DayScheme newDayScheme = daySchemeBuilder.setDate(dateUnderProcessing).build();

            daySchemeService.saveOrUpdate(newDayScheme);
        });
    }

    private int calculateNumberOfDaySchemesToCreate(LocalDate latestDaySchemeDate) {
        int numberOfFutureDaySchemes = (int) ChronoUnit.DAYS.between(LocalDate.now(), latestDaySchemeDate);

        return NUMBER_OF_REQUIRED_FUTURE_SCHEMES - numberOfFutureDaySchemes;
    }

}