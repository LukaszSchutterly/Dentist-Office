package dentist.office.service.entity.visit;

import dentist.office.events.VisitConfirmationViaMailEvent;
import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.visit.Visit;
import dentist.office.repositories.VisitRepo;
import dentist.office.service.entity.GenericServiceImpl;
import dentist.office.service.entity.day.DaySchemeService;
import dentist.office.service.entity.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class VisitServiceImpl extends GenericServiceImpl<Visit, Long> implements VisitService {

    private VisitRepo visitRepo;
    private PatientService patientService;
    private DaySchemeService daySchemeService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public VisitServiceImpl(VisitRepo visitRepo) {
        super(visitRepo);
        this.visitRepo = visitRepo;
    }

    @Autowired
    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setDaySchemeService(DaySchemeService daySchemeService) {
        this.daySchemeService = daySchemeService;
    }

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publicSave(Visit visit) {
        daySchemeService.removeAvailableVisitTime(visit.getVisitDate(), visit.getVisitTime());

        patientService.saveOrUpdate(visit.getPatient());
        visitRepo.save(visit);

        eventPublisher.publishEvent(new VisitConfirmationViaMailEvent(visit));
    }

    @Override
    public void saveOrUpdate(Visit visit) {
        patientService.saveOrUpdate(visit.getPatient());
        visitRepo.save(visit);
        daySchemeService.removeAvailableVisitTime(visit.getVisitDate(), visit.getVisitTime());
    }

    @Override
    public void removeById(Long id) {
        Visit visit = visitRepo.findOne(id);

        daySchemeService.addAvailableVisitTime(visit.getVisitDate(), visit.getVisitTime());
        visitRepo.delete(id);
    }

    @Override
    public void mergeVisits(Patient mergeInto, Patient mergeFrom) {
        List<Visit> visits = visitRepo.findAllByPatientIdAndConfirmed(mergeFrom.getId(), true);

        if (visits != null) {
            visits.forEach(v -> {
                v.setPatient(mergeInto);
                visitRepo.save(v);
            });
        }
    }

    @Override
    public List<Visit> getConfirmedVisitsByAccepted(boolean accepted) {
        return visitRepo.findAllByAcceptedAndConfirmed(accepted, true);
    }

    @Override
    public List<Visit> getVisitsByDateAndConfirmed(LocalDate date, boolean confirmed) {
        return visitRepo.findAllByVisitDateAndConfirmedOrderByVisitTime(date, confirmed);
    }

    @Override
    public List<Visit> getVisitsByPatientIdAndConfirmedOrderedByDate(Long id, boolean confirmed) {
        return visitRepo.findAllByPatientIdAndConfirmedOrderByVisitDateDescVisitTimeDesc(id, confirmed);
    }

    @Override
    public List<Visit> getVisitsByPatientIdAndConfirmed(Long id, boolean confirmed) {
        return visitRepo.findAllByPatientIdAndConfirmed(id, confirmed);
    }

    @Override
    public void removeVisitsByPatientId(Long patientId) {
        visitRepo.deleteByPatientId(patientId);
    }

}
