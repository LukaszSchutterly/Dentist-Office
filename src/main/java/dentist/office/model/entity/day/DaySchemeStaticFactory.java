package dentist.office.model.entity.day;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

@Component
public class DaySchemeStaticFactory {

    private static final int DEFAULT_STARTING_HOUR = 8;
    private static final int DEFAULT_SATURDAY_ENDING_HOUR = 14;
    private static final int DEFAULT_REGULAR_DAY_ENDING_HOUR = 18;

    public static DayScheme createDefaultDayScheme(LocalDate localDate) {
        DayScheme dayScheme = new DayScheme();
        dayScheme.setDate(localDate);
        dayScheme.setAvailableVisitTimes(prepareDefaultAvailableVisitTimes(localDate.getDayOfWeek()));
        return dayScheme;
    }

    private static Set<LocalTime> prepareDefaultAvailableVisitTimes(DayOfWeek dayOfWeek) {
        Set<LocalTime> availableVisitTimes = new TreeSet<>();

        switch (dayOfWeek) {
            case SATURDAY:
                prepareDefaultSaturdayAvailableVisitTimes(availableVisitTimes);
                break;
            case SUNDAY:
                break;
            default:
                prepareRegularAvailableVisitTimes(availableVisitTimes);
                break;
        }

        return availableVisitTimes;
    }

    private static void prepareDefaultSaturdayAvailableVisitTimes(Set<LocalTime> availableVisitTimes) {
        prepareVisitTimesUpToGivenHour(DEFAULT_SATURDAY_ENDING_HOUR, availableVisitTimes);
    }

    private static void prepareRegularAvailableVisitTimes(Set<LocalTime> availableVisitTimes) {
        prepareVisitTimesUpToGivenHour(DEFAULT_REGULAR_DAY_ENDING_HOUR, availableVisitTimes);
    }

    private static void prepareVisitTimesUpToGivenHour(int endingHour, Set<LocalTime> availableVisitTimes) {
        IntStream.range(DEFAULT_STARTING_HOUR, endingHour).forEach(i -> {
            availableVisitTimes.add(LocalTime.of(i, 0));
            availableVisitTimes.add(LocalTime.of(i, 30));
        });
    }

}
