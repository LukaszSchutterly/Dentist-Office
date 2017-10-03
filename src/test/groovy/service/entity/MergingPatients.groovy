package service.entity

import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.patient.Patient
import dentist.office.service.entity.patient.PatientService
import dentist.office.service.entity.visit.VisitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class MergingPatients extends Specification {

    @Autowired
    VisitService visitService
    @Autowired
    PatientService patientService

    Patient patientOne
    Patient patientTwo

    def setup(){
        String pesel="11122233344"

        patientOne=new Patient("John","Doe")
        patientOne.setPesel(pesel)
        patientOne.setDescription("a")
        patientOne.setEmail("mail@email.com")
        patientOne.setPhoneNumber("111111111")

        patientTwo=new Patient("John","Doe")
        patientTwo.setPesel(pesel)
        patientTwo.setEmail("email@mail.com")
        patientTwo.setDescription("b")
        patientTwo.setPhoneNumber("222222222")
    }


    def "saveOrUpdate should merge patients with same PESEL"(){
        setup:
        patientService.saveOrUpdate(patientOne)
        when:
        patientService.saveOrUpdate(patientTwo)
        then:
        patientService.getById(patientOne.getId())==null
        patientTwo.getEmail().equals(patientOne.getEmail())
        patientTwo.getPhoneNumber().equals(patientOne.getPhoneNumber())
        patientTwo.getDescription().equals("b a")
    }


}
