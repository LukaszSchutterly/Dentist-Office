package dao

import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.visit.Visit
import dentist.office.repositories.PatientRepo
import dentist.office.repositories.VisitRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class VisitRepoQueries extends Specification {

    @Autowired
    VisitRepo visitRepo
    @Autowired
    PatientRepo patientRepo

    LocalDate someOldDate = LocalDate.of(1410, 07, 15)
    LocalTime someLocalTime = LocalTime.of(12, 12)
    Patient patient
    Visit visitOne
    Visit visitTwo

    def setup() {
        patient = new Patient("John", "Doe")
        visitOne = new Visit(someOldDate, someLocalTime, patient)
        visitTwo = new Visit(someOldDate, someLocalTime, patient)
        visitTwo.setAccepted(true)
        visitTwo.setConfirmed(true)
        patientRepo.saveAndFlush(patient)
        visitRepo.saveAndFlush(visitOne)
        visitRepo.saveAndFlush(visitTwo)
    }

    def "should return accepted and confirmed visits only" (){
        when:
        List<Visit> visitList = visitRepo.findAllByAcceptedAndConfirmed(true,true)
        println (visitList)
        then:
        !visitList.contains(visitOne)
        visitList.contains(visitTwo)
    }

    def "should return confirmed visits by date"() {
        when:
        List<Visit> visitList = visitRepo.findAllByVisitDateAndConfirmedOrderByVisitTime(someOldDate,true)
        then:
        !visitList.contains(visitOne)
        visitList.contains(visitTwo)
    }

    def "should return visits by patient"() {
        when:
        List<Visit> visitList = visitRepo.findAllByPatientIdAndConfirmed(patient.getId(),true)
        then:
        !visitList.contains(visitOne)
        visitList.contains(visitTwo)
    }

}
