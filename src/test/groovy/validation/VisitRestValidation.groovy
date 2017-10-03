package validation

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.patient.Patient
import dentist.office.model.entity.visit.Visit
import dentist.office.service.entity.day.DaySchemeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class VisitRestValidation extends Specification {

    DaySchemeService daySchemeService
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    WebApplicationContext context
    Patient patient
    Visit visit
    String visitJsonString

    def setup() {
        daySchemeService = Mock(DaySchemeService)
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        patient = new Patient("John", "Doe")
        visit = new Visit(LocalDate.now(), LocalTime.now(), patient)
    }

    def "post should allow valid visit"() {
        setup:
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(200))
    }

    def "post should not allow null patient"() {
        setup:
        visit.patient = null
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

    def "post should not allow null visit date and time"() {
        setup:
        visit.visitTime = null
        visit.visitDate = null
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

    def "public post should validate patient"() {
        setup:
        patient.firstName = "aa"
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

    def "put should allow valid visit"() {
        setup:
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(put("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(200))
    }

    def "put should not allow null patient"() {
        setup:
        visit.patient = null
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(put("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

    def "put should not allow null visit date and time"() {
        setup:
        visit.visitTime = null
        visit.visitDate = null
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(put("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

    def "put post should validate patient"() {
        setup:
        patient.firstName = "aa"
        visitJsonString = objectMapper.writeValueAsString(visit)
        expect:
        mockMvc.perform(put("/visits").contentType(MediaType.APPLICATION_JSON).content(visitJsonString))
                .andExpect(status().is(400))
    }

}
