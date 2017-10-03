package dentist.office.repositories;

import dentist.office.model.entity.visit.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepo extends JpaRepository<Visit, Long> {

    List<Visit> findAllByVisitDateAndConfirmedOrderByVisitTime(LocalDate visitDate, boolean confirmed);

    List<Visit> findAllByAcceptedAndConfirmed(boolean accepted, boolean confirmed);

    List<Visit> findAllByPatientIdAndConfirmedOrderByVisitDateDescVisitTimeDesc(Long patientId, boolean confirmed);

    List<Visit> findAllByPatientIdAndConfirmed(Long patientId, boolean confirmed);

    void deleteByPatientId(Long patientId);

}
