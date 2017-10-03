package dentist.office.service.entity.patient;

import dentist.office.model.entity.patient.Patient;
import dentist.office.service.entity.GenericService;

import java.util.List;

public interface PatientService extends GenericService<Patient, Long> {

    List<Patient> getAllOrderByLastName();

}
