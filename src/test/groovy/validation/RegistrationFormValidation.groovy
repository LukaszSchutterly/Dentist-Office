package validation

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.service.entity.day.DaySchemeService
import org.json.JSONObject
import org.mockito.InjectMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import java.time.LocalDateTime

import static org.mockito.Matchers.any
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class RegistrationFormValidation extends Specification {

    @MockBean
    DaySchemeService daySchemeService
    MockMvc mockMvc
    @Autowired
    @InjectMocks
    WebApplicationContext context
    @Autowired
    ObjectMapper objectMapper
    JSONObject jsonObject

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()

        jsonObject = new JSONObject()
        jsonObject.put("visitDateTime", "05-10-2016 14:00")
        jsonObject.put("firstName", "John")
        jsonObject.put("lastName", "Doe")
        jsonObject.put("phoneNumber", "123123123")
        jsonObject.put("email", "email@mail.com")

        when(daySchemeService.isVisitTimeAvailable(any(LocalDateTime.class))).thenReturn(true)
    }

    def "valid registrationDTO should pass"() {
        setup:
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(200))
    }

    def "shouldn't accept firstName with white spaces only"() {
        setup:
        jsonObject.put("firstName", "     ")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept firstName with length greater than 25"() {
        setup:
        jsonObject.put("firstName", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept firstName with length less than 3"() {
        setup:
        jsonObject.put("firstName", "aa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept blank firstName"() {
        setup:
        jsonObject.put("firstName", "")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept lastName with white spaces only"() {
        setup:
        jsonObject.put("lastName", "     ")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept lastName with length greater than 30"() {
        setup:
        jsonObject.put("lastName", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept lastName with length less than 3"() {
        setup:
        jsonObject.put("lastName", "aa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept blank lastName"() {
        setup:
        jsonObject.put("lastName", "")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept email without @"() {
        setup:
        jsonObject.put("email", "dasdsadsa.dsa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept blank email"() {
        setup:
        jsonObject.put("email", "")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept email with length less than 7"() {
        setup:
        jsonObject.put("email", "aa@.a")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept  email blank with length more than 40"() {
        setup:
        jsonObject.put("email", "ddddddddddddddddddddddddddddd@dddddddddddddddddddddddddddddddddddd.pl")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept blank phoneNumber"() {
        setup:
        jsonObject.put("phoneNumber", "")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept phoneNumber without numbers"() {
        setup:
        jsonObject.put("phoneNumber", "aaaaaaaaaaaaaaa")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept phoneNumber with length lass than 8"() {
        setup:
        jsonObject.put("phoneNumber", "1234567")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

    def "shouldn't accept phoneNumber with length more than 15"() {
        setup:
        jsonObject.put("phoneNumber", "12345678910111213")
        expect:
        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString()))
                .andExpect(status().is(400))
    }

}