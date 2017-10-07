package rest

import com.fasterxml.jackson.databind.ObjectMapper
import dentist.office.DentistOfficeApplication
import dentist.office.controller.rest.DaySchemeController
import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.day.DaySchemeBuilder
import dentist.office.service.entity.day.DaySchemeService
import dentist.office.service.entity.token.VisitConfirmationTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class DaySchemeRestApi extends Specification {

    DaySchemeService daySchemeService
    VisitConfirmationTokenService visitConfirmationTokenService

    DaySchemeController daySchemeController
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    LocalDate someDate = LocalDate.of(1410, 07, 15)
    String stringDate = "15-07-1410"
    DayScheme oldDayScheme
    String oldDaySchemeJsonString

    def setup() {
        daySchemeService = Mock(DaySchemeService)
        visitConfirmationTokenService=Mock(VisitConfirmationTokenService)

        daySchemeController = new DaySchemeController(daySchemeService,visitConfirmationTokenService)

        mockMvc = MockMvcBuilders.standaloneSetup(daySchemeController).build()
    }

    def "get mapping should work"() {
        setup:
        oldDayScheme = DaySchemeBuilder.createDefault(someDate)
        when:
        mockMvc.perform(get("/daySchemes/" + stringDate))
                .andExpect(status().isOk())
        then:
        daySchemeService.getById(someDate) >> oldDayScheme
    }

    def "get should return NO_FOUND status for null dayScheme"() {
        when:
        mockMvc.perform(get("/daySchemes/" + stringDate))
                .andExpect(status().is(404))
        then:
        1 * daySchemeService.getById(someDate)
    }

    def "put mapping should work"() {
        setup:
        oldDayScheme = DaySchemeBuilder.createDefault(someDate)
        oldDaySchemeJsonString = objectMapper.writeValueAsString(oldDayScheme)
        when:
        mockMvc.perform(put("/daySchemes").contentType(MediaType.APPLICATION_JSON)
                .content(oldDaySchemeJsonString))
                .andExpect(status().isOk())
        then:
        1 * daySchemeService.saveOrUpdate(oldDayScheme)
    }


}
