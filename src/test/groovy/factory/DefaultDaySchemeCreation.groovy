package factory

import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.day.DaySchemeBuilder
import spock.lang.Specification

import java.time.LocalDate

class DefaultDaySchemeCreation extends Specification {

    private DayScheme daySchemeUnderTest

    def "default saturday should have 12 visit times available"() {
        setup:
        LocalDate someSaturday = LocalDate.of(2017, 8, 26)
        when:
        daySchemeUnderTest = DaySchemeBuilder.createDefault(someSaturday)
        then:
        daySchemeUnderTest.getAvailableVisitTimes().size() == 12
    }

    def "default sunday should not have available visit times"() {
        setup:
        LocalDate someSunday = LocalDate.of(2017, 8, 27)
        when:
        daySchemeUnderTest = DaySchemeBuilder.createDefault(someSunday)
        then:
        daySchemeUnderTest.getAvailableVisitTimes().size() == 0
    }

    def "default monday should allow 20 visits"() {
        setup:
        LocalDate someMonday = LocalDate.of(2017, 8, 28)
        when:
        daySchemeUnderTest = DaySchemeBuilder.createDefault(someMonday)
        then:
        daySchemeUnderTest.getAvailableVisitTimes().size() == 20
    }

}
