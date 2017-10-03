package service.task

import dentist.office.DentistOfficeApplication
import dentist.office.model.entity.day.DayScheme
import dentist.office.model.entity.day.DaySchemeStaticFactory
import dentist.office.service.entity.day.DaySchemeService
import dentist.office.service.task.DaySchemeCreatingTask
import dentist.office.service.task.DaySchemeDestroyingTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDate

@SpringBootTest(classes = DentistOfficeApplication.class)
@ContextConfiguration
class DaySchemeTasks extends Specification {

    @Autowired
    DaySchemeCreatingTask daySchemeCreatingTask
    @Autowired
    DaySchemeDestroyingTask daySchemeDestroyingTask
    @Autowired
    DaySchemeService daySchemeService

    def "running daySchemeCreatingTask should populate database"() {
        when:
        daySchemeCreatingTask.createDaySchemesForFuture()
        then:
        daySchemeService.getAll().size() != 0
    }

    def "running daySchemeDestroyingTask should remove old daySchemes in database"() {
        setup:
        LocalDate oldDate = LocalDate.of(1000, 01, 01)
        DayScheme oldDayScheme = DaySchemeStaticFactory.createDefaultDayScheme(oldDate)
        daySchemeService.saveOrUpdate(oldDayScheme)

        assert daySchemeService.getById(oldDate)!=null
        when:
        daySchemeDestroyingTask.destroyPastDaySchemes()
        then:
        daySchemeService.getById(oldDate)==null
    }

}
