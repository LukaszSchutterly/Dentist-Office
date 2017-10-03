package rest

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.controller.rest.visit.VisitConfirmationController
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.token.VisitConfirmationToken
import dentist.office.model.entity.visit.Visit
import dentist.office.service.entity.token.VisitConfirmationTokenService
import dentist.office.service.entity.visit.VisitService
import dentist.office.service.mail.MailSendingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class VisitConfirmationRestApi extends Specification {


    VisitConfirmationTokenService confirmationTokenService
    VisitService visitService
    MailSendingService mailSendingService
    VisitConfirmationToken visitConfirmationToken

    VisitConfirmationController visitConfirmationController
    MockMvc mockMvc

    Patient patient
    Visit visit
    String visitJsonString

    @Autowired
    ObjectMapper objectMapper

    def setup() {
        confirmationTokenService = Mock(VisitConfirmationTokenService)
        visitService = Mock(VisitService)
        mailSendingService = Mock(MailSendingService)


        visitConfirmationController = new VisitConfirmationController(confirmationTokenService, visitService)
        visitConfirmationController.setMailSendingService(mailSendingService)
        mockMvc = MockMvcBuilders.standaloneSetup(visitConfirmationController).build()

        patient = new Patient("John", "Doe")
        visit = new Visit(LocalDate.now(), LocalTime.now(), patient)
        visitJsonString = objectMapper.writeValueAsString(visit)
    }

    def "confirm should update and set confirm field on visit to true"() {
        setup:
        visit = Mock(Visit)
        visitConfirmationToken = new VisitConfirmationToken(visit)
        confirmationTokenService.getByToken(_) >> visitConfirmationToken
        when:
        MvcResult result = mockMvc.perform(get("/confirm/?token=someString"))
                .andExpect(status().is(200))
                .andReturn()
        then:
        result.getResponse().getContentAsString() == "true"
        1 * visit.setConfirmed(true)
        1 * visitService.saveOrUpdate(visit)
    }


    def "confirm should return false on not existing visitConfirmToken"() {
        setup:
        confirmationTokenService.getByToken(_) >> null
        when:
        MvcResult result = mockMvc.perform(get("/confirm/?token=someString"))
                .andExpect(status().is(200))
                .andReturn()
        then:
        result.getResponse().getContentAsString() == "false"

    }

}
