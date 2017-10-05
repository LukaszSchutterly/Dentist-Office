package dentist.office.service.entity.visit;

import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.visit.Visit;
import dentist.office.service.entity.GenericService;

import java.time.LocalDate;
import java.util.List;

public interface VisitService extends GenericService<Visit, Long> {

    List<Visit> getConfirmedVisitsByAccepted(boolean accepted);

    List<Visit> getVisitsByDateAndConfirmed(LocalDate date, boolean confirmed);

    List<Visit> getVisitsByPatientIdAndConfirmedOrderedByDate(Long id, boolean confirmed);

    void removeVisitsByPatientId(Long patientId);

    void publicSave(Visit visit);

}
