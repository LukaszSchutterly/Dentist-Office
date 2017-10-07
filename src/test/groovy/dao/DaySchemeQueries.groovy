package dao

import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.day.DaySchemeBuilder
import dentist.office.repositories.DaySchemeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
@Transactional
class DaySchemeQueries extends Specification {

    @Autowired
    DaySchemeRepo daySchemeRepo

    def "should return past day schemes"() {
        setup:
        LocalDate someOldDateOne = LocalDate.of(1410, 07, 15)
        DayScheme daySchemeOne = DaySchemeBuilder.createDefault(someOldDateOne)
        when:
        daySchemeRepo.saveAndFlush(daySchemeOne)
        List<DayScheme> daySchemes = daySchemeRepo.findPastDaySchemes()
        then:
        daySchemes.contains(daySchemeOne)
    }

    def "should return latest day scheme"(){
        setup:
        LocalDate futureDate = LocalDate.of(2200, 01, 01)
        DayScheme dayScheme = DaySchemeBuilder.createDefault(futureDate)
        when:
        daySchemeRepo.saveAndFlush(dayScheme)
        LocalDate localeDate=daySchemeRepo.findLatestDaySchemeDate();
        then:
        localeDate.equals(futureDate)
    }

}
