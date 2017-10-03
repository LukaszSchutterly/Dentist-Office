package rest

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.controller.rest.PatientController
import dentist.office.model.entity.patient.Patient
import dentist.office.service.entity.patient.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class PatientRestApi extends Specification {

    PatientService patientService
    PatientController patientController
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper
    Patient patient
    String patientJsonString

    def setup() {
        patientService = Mock(PatientService)
        patientController = new PatientController(patientService)
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build()

        patient = new Patient("John", "Doe")
        patientJsonString = objectMapper.writeValueAsString(patient)
    }

    def "post mapping should work"() {
        when:
        mockMvc.perform(post("/patients").contentType(MediaType.APPLICATION_JSON_VALUE).content(patientJsonString))
                .andExpect(status().isOk())
        then:
        1 * patientService.saveOrUpdate(patient)
    }

    def "put mapping should work"() {
        when:
        mockMvc.perform(put("/patients").contentType(MediaType.APPLICATION_JSON).content(patientJsonString))
                .andExpect(status().isOk())
        then:
        1 * patientService.saveOrUpdate(patient)
    }

    def "delete mapping should work"() {
        when:
        mockMvc.perform(delete("/patients/0"))
                .andExpect(status().isOk())
        then:
        1 * patientService.removeById(0L)
    }

}
