package dentist.office.controller.rest;

import dentist.office.model.DentalService;
import dentist.office.model.entity.patient.Patient;
import dentist.office.model.entity.visit.Visit;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/services")
public class DentalServiceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DentalService> getAllDentalServices() {
        return Arrays.asList(DentalService.values());
    }

}
