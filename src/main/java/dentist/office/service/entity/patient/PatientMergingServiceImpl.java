package dentist.office.service.entity.patient;

import dentist.office.exception.IllegalMergeException;
import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.visit.Visit;
import dentist.office.repositories.PatientRepo;
import dentist.office.repositories.VisitRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMergingServiceImpl implements PatientMergingService {

    private PatientRepo patientRepo;
    private VisitRepo visitRepo;

    public PatientMergingServiceImpl(PatientRepo patientRepo, VisitRepo visitRepo) {
        this.patientRepo = patientRepo;
        this.visitRepo = visitRepo;
    }

    @Override
    public void saveOrMergeByPesel(Patient patient) throws IllegalMergeException {
        Patient persistedPatientWithSamePesel = patientRepo.findByPesel(patient.getPesel());

        if (persistedPatientWithSamePesel == null || persistedPatientWithSamePesel.getId() == patient.getId())
            patientRepo.save(patient);
        else if (!persistedPatientWithSamePesel.equalsWithNameDetails(patient))
            throw new IllegalMergeException();
        else
            merge(persistedPatientWithSamePesel, patient);
    }

    private void merge(Patient mergeInto, Patient mergeFrom) {
        List<Visit> visits = visitRepo.findAllByPatientId(mergeFrom.getId());

        if(visits!=null){

            visits.forEach(v -> {
                v.setPatient(mergeInto);
                visitRepo.save(v);
            });
        }

        visitRepo.deleteByPatientId(mergeFrom.getId());
        patientRepo.delete(mergeFrom);

        if (mergeFrom.getDescription() != null)
            mergeInto.addDescription(mergeFrom.getDescription());

        if (mergeFrom.getPhoneNumber() != null && !mergeFrom.getPhoneNumber().equals(mergeInto.getPhoneNumber())) {
            mergeInto.addDescription("Stary numer telefonu: " + mergeInto.getPhoneNumber());
            mergeInto.setPhoneNumber(mergeFrom.getPhoneNumber());
        }

        if (mergeFrom.getEmail() != null) {
            mergeInto.setEmail(mergeFrom.getEmail());
        }

        patientRepo.save(mergeInto);
    }

}
