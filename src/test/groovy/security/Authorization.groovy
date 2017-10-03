package security

import dentist.office.DentistOfficeApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class Authorization extends Specification {

    MockMvc mockMvc
    @Autowired
    WebApplicationContext context

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build()
    }

    def "should not require authentication for url /"() {
        expect:
        mockMvc.perform(get("/")).andExpect(status().is(200))
    }

    def "should require authentication for url /doctor"() {
        expect:
        mockMvc.perform(get("/doctor")).andExpect(status().is(401))
    }

    def "should require authentication for url /daySchemes"() {
        expect:
        mockMvc.perform(put("/daySchemes")).andExpect(status().is(401))
    }

    def "should require authentication for url /daySchemes/notConfirmedVisitTimes"() {
        expect:
        mockMvc.perform(get("/daySchemes/notConfirmedVisitTimes")).andExpect(status().is(401))
    }

    def "should not require authentication for url /daySchemes/04-11-2015"() {
        expect:
        mockMvc.perform(get("/daySchemes/04-11-2015")).andExpect(status().is(404))
    }

    def "should require authentication for url /visits"() {
        expect:
        mockMvc.perform(put("/visits")).andExpect(status().is(401))
    }

    def "should require authentication for url /visits/2"() {
        expect:
        mockMvc.perform(delete("/visits/2")).andExpect(status().is(401))
    }

    def "should require authentication for url /visits/byDate/04-11-2015"() {
        expect:
        mockMvc.perform(get("/visits/byDate/04-11-2015")).andExpect(status().is(401))
    }

    def "should require authentication for url /visits/byAccepted?accepted=true"() {
        expect:
        mockMvc.perform(get("/visits/byAccepted?accepted=true")).andExpect(status().is(401))
    }

    def "should require authentication for url /visits/byPatient/2"() {
        expect:
        mockMvc.perform(get("/visits/byPatient/2")).andExpect(status().is(401))
    }

    def "should not require authentication for url /confirm?token=adsads"() {
        expect:
        mockMvc.perform(get("/confirm")).andExpect(status().is(400))
    }

    def "should not require authentication for url /resendMail"() {
        expect:
        mockMvc.perform(post("/resendMail")).andExpect(status().is(400))
    }

    def "should require authentication for url /visits/acceptOrReject"() {
        expect:
        mockMvc.perform(put("/visits/acceptOrReject")).andExpect(status().is(401))
    }

    def "should not require authentication for url /registration"() {
        expect:
        mockMvc.perform(post("/registration")).andExpect(status().is(415))
    }

    def "should require authentication for url /templates/doctor/**"() {
        expect:
        mockMvc.perform(get("/templates/doctor/visits/a")).andExpect(status().is(401))
    }

    def "should require authentication for url /modules/doctor/visits/**"() {
        expect:
        mockMvc.perform(get("/modules/doctor/visits/a")).andExpect(status().is(401))
    }

    def "should require authentication for url /modules/doctor/patients/**"() {
        expect:
        mockMvc.perform(get("/modules/doctor/patients/a")).andExpect(status().is(401))
    }

    def "should not require authentication for url /templates/patient/**"() {
        expect:
        mockMvc.perform(get("/templates/patient/a")).andExpect(status().is(404))
    }

    def "should not require authentication for url /modules/patient/**"() {
        expect:
        mockMvc.perform(get("/modules/patient/a")).andExpect(status().is(404))
    }


}
