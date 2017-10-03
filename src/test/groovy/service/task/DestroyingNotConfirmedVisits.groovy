package service.task

import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.token.VisitConfirmationToken
import dentist.office.model.entity.visit.Visit
import dentist.office.service.entity.token.VisitConfirmationTokenService
import dentist.office.service.entity.visit.VisitService
import dentist.office.service.task.NotConfirmedVisitDestroyingTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class DestroyingNotConfirmedVisits extends Specification {

    @Autowired
    VisitConfirmationTokenService visitConfirmationTokenService
    @Autowired
    NotConfirmedVisitDestroyingTask notConfirmedVisitDestroyingTask

    VisitConfirmationToken visitConfirmationToken

    def setup() {
        Patient patient = new Patient("John", "Doe")
        Visit visit = new Visit(LocalDate.now(), LocalTime.now(), patient)

        visitConfirmationToken = new VisitConfirmationToken(visit)
        visitConfirmationToken.expiryDateTime=LocalDateTime.now().minusDays(2)

        visitConfirmationTokenService.saveOrUpdate(visitConfirmationToken)
    }

    def "should remove expired tokens"() {
        when:
        notConfirmedVisitDestroyingTask.removeExpiredTokens()
        then:
        visitConfirmationTokenService.getById(visitConfirmationToken.getId())==null
    }

}
