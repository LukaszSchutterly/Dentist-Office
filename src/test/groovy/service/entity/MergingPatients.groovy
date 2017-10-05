package service.entity

import dentist.office.DentistOfficeApplication
import dentist.office.exception.IllegalMergeException
import dentist.office.model.entity.patient.Patient
import dentist.office.repositories.PatientRepo
import dentist.office.repositories.VisitRepo
import dentist.office.service.entity.patient.PatientMergingService
import dentist.office.service.entity.patient.PatientMergingServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest(classes = DentistOfficeApplication.class)
class MergingPatients extends Specification {

    @Autowired
    private PatientMergingService patientMergingService
    private PatientRepo patientRepo
    private VisitRepo visitRepo

    Patient patientOne
    String pesel

    def setup(){
        patientOne=new Patient("John","Doe")
        pesel="12312312312"
        patientOne.setPesel(pesel)

        patientRepo=Mock(PatientRepo)
        visitRepo=Mock(VisitRepo)

        patientMergingService=new PatientMergingServiceImpl(patientRepo,visitRepo)
    }


    def "should just save if there isn't patient with same PESEL"(){
        when:
        patientMergingService.saveOrMergeByPesel(patientOne)
        then:
        1 * patientRepo.findByPesel(pesel) >> null
        1 * patientRepo.save(patientOne)
        0 * patientRepo._(*_)
    }

    def "should just save if found same patient in database"(){
        when:
        patientMergingService.saveOrMergeByPesel(patientOne)
        then:
        1 * patientRepo.findByPesel(pesel) >> patientOne
        1 * patientRepo.save(patientOne)
        0 * patientRepo._(*_)
    }

    def "should throw exception if trying to merge patients with different name details"(){
        setup:
        Patient patientTwo=new Patient("Ann","Doe")
        patientTwo.id=2
        when:
        patientMergingService.saveOrMergeByPesel(patientOne)
        then:
        1 * patientRepo.findByPesel(pesel) >> patientTwo
        thrown(IllegalMergeException)
        0 * patientRepo._(*_)
    }

    def "should delete mergedFrom patient form database and save mergedInto"(){
        setup:
        Patient patientTwo=new Patient("John","Doe")
        patientTwo.id=2
        when:
        patientMergingService.saveOrMergeByPesel(patientOne)
        then:
        1 * patientRepo.findByPesel(pesel) >> patientTwo
        1 * patientRepo.delete(patientOne)
        1 * patientRepo.save(patientTwo)
        0 * patientRepo._(*_)
    }

}
