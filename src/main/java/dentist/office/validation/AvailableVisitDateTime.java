package dentist.office.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AvailableVisitDateTimeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableVisitDateTime {

    String message() default "Visit date or time not available";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
