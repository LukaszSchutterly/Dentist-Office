package service.entity

import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.visit.Visit
import dentist.office.repositories.VisitRepo
import dentist.office.service.entity.day.DaySchemeService
import dentist.office.service.entity.patient.PatientService
import dentist.office.service.entity.visit.VisitService
import dentist.office.service.entity.visit.VisitServiceImpl
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

class VisitServiceResponsibilities extends Specification {

    VisitRepo visitRepo
    PatientService patientService
    DaySchemeService daySchemeService
    ApplicationEventPublisher eventPublisher
    DayScheme dayScheme

    VisitService visitService
    Patient patient
    Visit visit

    def setup() {
        visitRepo = Mock(VisitRepo)
        patientService = Mock(PatientService)
        daySchemeService = Mock(DaySchemeService)
        eventPublisher = Mock(ApplicationEventPublisher)
        dayScheme = Mock(DayScheme)

        visitService = new VisitServiceImpl(visitRepo)
        visitService.setPatientService(patientService)
        visitService.setDaySchemeService(daySchemeService)
        visitService.setEventPublisher(eventPublisher)

        patient = new Patient("John", "Doe")
        visit = new Visit(LocalDate.now(), LocalTime.now(), patient)
    }

    def "public save should trigger VisitConfirmationViaMailEvent when visit is not confirmed"() {
        when:
        visitService.publicSave(visit)
        then:
        1 * eventPublisher.publishEvent(_)
    }

    def "update should update patient"() {
        when:
        visitService.saveOrUpdate(visit)
        then:
        1 * patientService.saveOrUpdate(patient)
    }

    def "remove should add available visit time"() {
        setup:
        visitRepo.findOne(visit.getId()) >> visit
        when:
        visitService.removeById(visit.getId())
        then:
        1 * daySchemeService.addAvailableVisitTime(visit.getVisitDate(), visit.getVisitTime())
        1 * visitRepo.delete(visit.getId())
    }

    def "saveOrUpdate should remove visit time"() {
        setup:
        visitRepo.findOne(visit.getId()) >> visit
        when:
        visitService.saveOrUpdate(visit)
        then:
        1 * daySchemeService.removeAvailableVisitTime(visit.getVisitDate(),visit.getVisitTime())
        1 * patientService.saveOrUpdate(visit.getPatient())
        1 * visitRepo.save(visit)
    }

}
