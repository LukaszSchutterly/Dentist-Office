package dentist.office.service.entity.day;

import dentist.office.model.entity.day.DayScheme;
import dentist.office.repositories.DaySchemeRepo;
import dentist.office.service.entity.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class DaySchemeServiceImpl extends GenericServiceImpl<DayScheme, LocalDate> implements DaySchemeService {

    private DaySchemeRepo daySchemeRepo;

    @Autowired
    public DaySchemeServiceImpl(DaySchemeRepo daySchemeRepo) {
        super(daySchemeRepo);
        this.daySchemeRepo = daySchemeRepo;
    }

    @Override
    public LocalDate getLatestDaySchemeDate() {
        return daySchemeRepo.findLatestDaySchemeDate();
    }

    @Override
    public List<DayScheme> getPastDaySchemes() {
        return daySchemeRepo.findPastDaySchemes();
    }

    @Override
    public void addAvailableVisitTime(LocalDate localDate, LocalTime localTime) {
        DayScheme dayScheme = daySchemeRepo.findOne(localDate);

        if (dayScheme != null) {
            dayScheme.addAvailableVisitTime(localTime);
            daySchemeRepo.save(dayScheme);
        }

    }

    @Override
    public void removeAvailableVisitTime(LocalDate localDate, LocalTime localTime) {
        DayScheme dayScheme = daySchemeRepo.findOne(localDate);

        if (dayScheme != null) {
            dayScheme.removeAvailableVisitTime(localTime);
            daySchemeRepo.save(dayScheme);
        }
    }

    public boolean isVisitTimeAvailable(LocalDateTime localDateTime) {
        DayScheme dayScheme = daySchemeRepo.findOne(localDateTime.toLocalDate());

        return dayScheme != null && dayScheme.getAvailableVisitTimes().contains(localDateTime.toLocalTime());
    }

}
