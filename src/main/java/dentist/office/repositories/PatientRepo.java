package dentist.office.repositories;

import dentist.office.model.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {

    Patient findByPesel(String PESEL);

}
