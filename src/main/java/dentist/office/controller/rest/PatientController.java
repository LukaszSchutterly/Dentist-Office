package dentist.office.controller.rest;

import dentist.office.model.entity.patient.Patient;
import dentist.office.service.entity.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postPatient(@Valid @RequestBody Patient patient) {
        patientService.saveOrUpdate(patient);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putPatient(@Valid @RequestBody Patient patient) {
        patientService.saveOrUpdate(patient);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.removeById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Patient> getAllSortedByLastName() {
        return patientService.getAllOrderByLastName();
    }


}
