package dentist.office.model.entity.day;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

@Component
public class DaySchemeBuilder {

    private static final int DEFAULT_STARTING_HOUR = 8;
    private static final int DEFAULT_SATURDAY_ENDING_HOUR = 14;
    private static final int DEFAULT_REGULAR_DAY_ENDING_HOUR = 18;

    private LocalDate date;
    private Set<LocalTime> availableVisitTimes;

    public DaySchemeBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public DayScheme build() {
        if (this.date == null) return null;

        prepareDefaultAvailableVisitTimes();

        return new DayScheme(this.date, this.availableVisitTimes);
    }

    private void prepareDefaultAvailableVisitTimes() {
        DayOfWeek dayOfWeek = this.date.getDayOfWeek();
        this.availableVisitTimes = new TreeSet<>();

        switch (dayOfWeek) {
            case SATURDAY:
                prepareVisitTimesUpToGivenHour(DEFAULT_SATURDAY_ENDING_HOUR);
                break;
            case SUNDAY:
                break;
            default:
                prepareVisitTimesUpToGivenHour(DEFAULT_REGULAR_DAY_ENDING_HOUR);
                break;
        }
    }

    private void prepareVisitTimesUpToGivenHour(int endingHour) {
        IntStream.range(DEFAULT_STARTING_HOUR, endingHour).forEach(i -> {
            availableVisitTimes.add(LocalTime.of(i, 0));
            availableVisitTimes.add(LocalTime.of(i, 30));
        });
    }

}
