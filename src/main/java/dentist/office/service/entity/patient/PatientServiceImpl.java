package dentist.office.service.entity.patient;

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

    @Override
    public List<Patient> getAllOrderByLastName() {
        List<Patient> patients = patientRepo.findAll();

        patients.sort((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()));

        return patients;
    }

    @Override
    public void saveOrUpdate(Patient patient) {

        if (patient.getPesel() != null) {
            Patient patientInDb = patientRepo.findByPesel(patient.getPesel());

            if (patientInDb != null && !patientInDb.equals(patient)) {
                mergePatients(patient, patientInDb);
            } else {
                patientRepo.save(patient);
            }

        } else
            patientRepo.save(patient);
    }

    private void mergePatients(Patient mergeInto, Patient mergeFrom) {

        if (mergeFrom.getPhoneNumber() != null)
            mergeInto.setPhoneNumber(mergeFrom.getPhoneNumber());

        if (mergeFrom.getEmail() != null)
            mergeInto.setEmail(mergeFrom.getEmail());

        if (mergeFrom.getDescription() != null)
            mergeInto.addDescription(mergeFrom.getDescription());

        patientRepo.save(mergeInto);
        visitService.mergeVisits(mergeInto, mergeFrom);
        patientRepo.delete(mergeFrom);
    }


    @Override
    public void removeById(Long id) {
        visitService.removeVisitsByPatientId(id);
        patientRepo.delete(id);
    }

}
