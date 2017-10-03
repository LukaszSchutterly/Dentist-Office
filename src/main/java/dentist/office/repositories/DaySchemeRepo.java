package dentist.office.repositories;

import dentist.office.model.entity.day.DayScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DaySchemeRepo extends JpaRepository<DayScheme, LocalDate> {

    @Query("select max(d.date) from DayScheme d")
    LocalDate findLatestDaySchemeDate();

    @Query("select d from DayScheme d where d.date<current_date ")
    List<DayScheme> findPastDaySchemes();

}
