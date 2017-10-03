package validation

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.patient.Patient
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class PatientRestValidation extends Specification {

    DaySchemeService daySchemeService
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @Autowired
    WebApplicationContext context
    Patient patient
    String patientJsonString

    def setup() {
        daySchemeService = Mock(DaySchemeService)
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        patient = new Patient("John", "Doe")
    }

    def "post should allow valid patient"() {
        setup:
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(200))
    }

    def "post shouldn't accept firstName with white spaces only"() {
        setup:
        patient.firstName="     "
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "post shouldn't accept firstName with length greater than 25"() {
        setup:
        patient.firstName="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "post shouldn't accept firstName with length less than 3"() {
        setup:
        patient.firstName="aa"
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "post shouldn't accept blank firstName"() {
        setup:
        patient.firstName=""
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "put should allow valid patient"() {
        setup:
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(200))
    }

    def "put shouldn't accept lastName with white spaces only"() {
        setup:
        patient.lastName="     "
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "put shouldn't accept lastName with length greater than 25"() {
        setup:
        patient.lastName="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "put shouldn't accept lastName with length less than 3"() {
        setup:
        patient.lastName="aa"
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

    def "put shouldn't accept blank lastName"() {
        setup:
        patient.lastName=""
        patientJsonString = objectMapper.writeValueAsString(patient)
        expect:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().is(400))
    }

}
