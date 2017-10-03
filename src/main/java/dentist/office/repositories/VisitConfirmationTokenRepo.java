package dentist.office.repositories;

import dentist.office.model.entity.token.VisitConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface VisitConfirmationTokenRepo extends JpaRepository<VisitConfirmationToken, Long> {


    List<VisitConfirmationToken> findByExpiryDateTimeLessThan(LocalDateTime localDateTime);

    List<VisitConfirmationToken> findByVisitVisitDate(LocalDate localDate);

    VisitConfirmationToken findByToken(String token);

    VisitConfirmationToken findByVisitVisitDateAndVisitVisitTime(LocalDate date, LocalTime time);

}
