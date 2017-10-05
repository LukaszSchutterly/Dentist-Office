package dentist.office.loging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* dentist.office.controller.rest.*.*(..))")
    public void restControllersPointcut() {

    }

    @Pointcut("execution(* dentist.office.controller.advice.*.*(..))")
    public void adviceControllersPointcut() {

    }

    @Pointcut("execution(* dentist.office.service.*.*.*(..)) || execution(* dentist.office.service.*.*.*.*(..))")
    public void servicesPointcut() {
    }

    @Before("restControllersPointcut() || adviceControllersPointcut() || servicesPointcut()")
    public void loggingRestControllers(JoinPoint joinPoint) {
        String className = joinPoint.getThis().getClass().getSimpleName().split("\\$")[0];
        List<Object> args = Arrays.asList(joinPoint.getArgs());

        logger.info("**************************************************************");
        logger.info(className + " " + joinPoint.getSignature().getName());
        logger.info("WITH ARGUMENTS:");
        logger.info("----------------------------------------");
        args.forEach(a -> logger.info(a.toString()));
    }

}
