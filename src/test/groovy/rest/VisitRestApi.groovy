package rest

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.controller.rest.visit.VisitController
import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.visit.Visit
import dentist.office.service.entity.day.DaySchemeService
import dentist.office.service.entity.visit.VisitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class VisitRestApi extends Specification {

    VisitService visitService
    VisitController visitController
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper
    Patient patient
    Visit visit
    DayScheme dayScheme
    String visitJsonString

    def setup() {
        visitService = Mock(VisitService)
        visitController = new VisitController(visitService)
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build()

        patient = new Patient("John", "Doe")
        visit = new Visit(LocalDate.now(),LocalTime.of(12,20), patient)
        visitJsonString = objectMapper.writeValueAsString(visit)
    }

    def "post mapping should work"() {
        setup:
        dayScheme.getAvailableVisitTimes() >> Collections.singletonList(LocalTime.of(12,20))
        when:
        mockMvc.perform(post("/visits").content(visitJsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        then:
        1 * visitService.saveOrUpdate(visit)
    }

    def "post should set accepted field true"() {
        setup:
        def arg
        visitJsonString = objectMapper.writeValueAsString(visit)
        when:
        mockMvc.perform(post("/visits").content(visitJsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
        then:
        1 * visitService.saveOrUpdate(_ as Visit) >> { argument -> arg=argument }
        arg.accepted
    }

    def "post should set confirmed field true"() {
        setup:
        def arg
        visitJsonString = objectMapper.writeValueAsString(visit)
        when:
        mockMvc.perform(post("/visits").content(visitJsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
        then:
        1 * visitService.saveOrUpdate(_ as Visit) >> { argument -> arg=argument }
        arg.confirmed
    }

    def "put mapping should work"() {
        when:
        mockMvc.perform(put("/visits").content(visitJsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        then:
        1 * visitService.saveOrUpdate(visit)
    }

    def "delete mapping should work"() {
        when:
        mockMvc.perform(delete("/visits/0"))
                .andExpect(status().isOk())
        then:
        1 * visitService.removeById(0L)
    }

    def "should return confirmed visits by date"() {
        setup:
        LocalDate someDate = LocalDate.of(1410, 7, 15)
        String dateString = "15-07-1410"
        when:
        mockMvc.perform(get("/visits/byDate/" + dateString).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        then:
        1 * visitService.getVisitsByDateAndConfirmed(someDate,true)
    }

    def "should return not accepted, confirmed visits"() {
        when:
        mockMvc.perform(get("/visits/byAccepted?accepted=false").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        then:
        1 * visitService.getConfirmedVisitsByAccepted(false)
    }

    def "get confirmed visits by patient should work"() {
        when:
        mockMvc.perform(get("/visits/byPatient/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        then:
        1 * visitService.getVisitsByPatientIdAndConfirmedOrderedByDate(0,true)
    }

}
