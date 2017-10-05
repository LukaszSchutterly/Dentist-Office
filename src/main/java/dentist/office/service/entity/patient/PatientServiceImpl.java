package dentist.office.service.entity.patient;

import dentist.office.exception.IllegalMergeException;
import dentist.office.model.entity.patient.Patient;
import dentist.office.repositories.PatientRepo;
import dentist.office.service.entity.GenericServiceImpl;
import dentist.office.service.entity.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl extends GenericServiceImpl<Patient, Long> implements PatientService {

    private PatientRepo patientRepo;
    private PatientMergingService patientMergingService;
    private VisitService visitService;

    @Autowired
    public PatientServiceImpl(PatientRepo patientRepo) {
        super(patientRepo);
        this.patientRepo = patientRepo;
    }

    @Autowired
    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Autowired
    public void setPatientMergingService(PatientMergingService patientMergingService) {
        this.patientMergingService = patientMergingService;
    }

    @Override
    public List<Patient> getAllOrderByLastName() {
        List<Patient> patients = patientRepo.findAll();

        patients.sort((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()));

        return patients;
    }

    @Override
    public void removeById(Long id) {
        visitService.removeVisitsByPatientId(id);
        patientRepo.delete(id);
    }

    @Override
    public void saveOrUpdate(Patient patient) {

        if (patient.getPesel() != null) {
            tryToMergePatient(patient);
        } else
            patientRepo.save(patient);
    }

    private void tryToMergePatient(Patient patient){

        try {
            patientMergingService.saveOrMergeByPesel(patient);
        } catch (IllegalMergeException e) {
            patient.setPesel(null);
            patientRepo.save(patient);
            e.getMessage();
        }
    }

}
