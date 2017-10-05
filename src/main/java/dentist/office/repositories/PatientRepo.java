package dentist.office.repositories;

import dentist.office.model.entity.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface PatientRepo extends JpaRepository<Patient, Long> {

    Patient findByPesel(String Pesel);

}
