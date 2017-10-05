package service.entity

import dentist.office.model.entity.patient.Patient
import dentist.office.repositories.PatientRepo
import dentist.office.service.entity.patient.PatientService
import dentist.office.service.entity.patient.PatientServiceImpl
import dentist.office.service.entity.visit.VisitService
import spock.lang.Specification

class PatientServiceResponsibilities extends Specification {

    PatientRepo patientRepo
    VisitService visitService
    PatientService patientService

    Patient patient

    def setup() {
        patientRepo = Mock(PatientRepo)
        visitService = Mock(VisitService)

        patientService = new PatientServiceImpl(patientRepo)
        patientService.setVisitService(visitService)

        patient = new  Patient("John","doe")
    }

    def "deleting patient should trigger deleting patient's visits"() {
        when:
        patientService.removeById(patient.getId())
        then:
        1 * visitService.removeVisitsByPatientId(patient.getId())
    }

    def "getAllOrderByLastName should return patients sorted ignore case by last name"() {
        setup:
        Patient patientTwo = new Patient("Ann", "Aoe")
        patientTwo.id=1
        when:
        List<Patient> patientList = patientService.getAllOrderByLastName()
        then:
        1 * patientRepo.findAll() >> Arrays.asList(patient, patientTwo)
        patientList.indexOf(patient) > patientList.indexOf(patientTwo)
    }

}
