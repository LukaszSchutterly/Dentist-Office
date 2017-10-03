package dentist.office.service.entity.day;

import dentist.office.model.entity.day.DayScheme;
import dentist.office.service.entity.GenericService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface DaySchemeService extends GenericService<DayScheme, LocalDate> {

    LocalDate getLatestDaySchemeDate();

    List<DayScheme> getPastDaySchemes();

    void addAvailableVisitTime(LocalDate localDate, LocalTime localTime);

    void removeAvailableVisitTime(LocalDate localDate, LocalTime localTime);

    boolean isVisitTimeAvailable(LocalDateTime localDateTime);

}
