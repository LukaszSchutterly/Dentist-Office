package dentist.office.service.entity.patient;

import dentist.office.exception.IllegalMergeException;
import dentist.office.model.entity.patient.Patient;

public interface PatientMergingService {

    void saveOrMergeByPesel(Patient patient) throws IllegalMergeException;

}
