package ru.maslov.springstudyprpject.apects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.maslov.springstudyprpject.entities.Appointment;

@Aspect
@Component
public class AppointmentServiceAspect {

    private Logger logger = LoggerFactory.getLogger(AppointmentServiceAspect.class);

    @Pointcut("execution(public * ru.maslov.springstudyprpject.servicies.AppointmentService.saveAppointment(..))")
    public void callSaveAppointmentMethod(){}

    @AfterReturning(pointcut = "callSaveAppointmentMethod()",
                    returning = "appointment")
    public void loggingCreationOfAppointments(JoinPoint joinPoint, Appointment appointment) {
        logger.info("Appointment : " + appointment + " was successful saved");
    }

    @AfterThrowing(pointcut = "callSaveAppointmentMethod()",
            throwing = "exception")
    public void loggingExceptionOnCreationOfAppointments(JoinPoint joinPoint, Throwable exception) {
        logger.info("Thrown an exception while save appointment: " + exception.getMessage());
    }

}
