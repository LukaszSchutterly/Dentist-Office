package dentist.office.validation;

import dentist.office.service.entity.day.DaySchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Component
public class AvailableVisitDateTimeValidator implements ConstraintValidator<AvailableVisitDateTime, LocalDateTime> {

    private DaySchemeService daySchemeService;

    @Autowired
    public void setDaySchemeService(DaySchemeService daySchemeService) {
        this.daySchemeService = daySchemeService;
    }

    @Override
    public void initialize(AvailableVisitDateTime constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {

        if (value == null)
            return false;

        return daySchemeService.isVisitTimeAvailable(value);
    }
}
