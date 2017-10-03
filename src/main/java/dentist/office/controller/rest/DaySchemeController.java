package dentist.office.controller.rest;

import dentist.office.model.entity.day.DayScheme;
import dentist.office.service.entity.day.DaySchemeService;
import dentist.office.service.entity.token.VisitConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/daySchemes")
public class DaySchemeController {

    private DaySchemeService daySchemeService;
    private VisitConfirmationTokenService confirmationTokenService;

    @Autowired
    public DaySchemeController(DaySchemeService daySchemeService, VisitConfirmationTokenService confirmationTokenService) {
        this.daySchemeService = daySchemeService;
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DayScheme> getDayScheme(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate id) {
        DayScheme dayScheme = daySchemeService.getById(id);

        if (dayScheme == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(dayScheme, HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putDayScheme(@RequestBody DayScheme dayScheme) {
        daySchemeService.saveOrUpdate(dayScheme);
    }

    @GetMapping(value = "/notConfirmedVisitTimes")
    public List<String> getNotConfirmedVisitTimesByDate(@RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                LocalDate localDate) {
        return confirmationTokenService.getNotConfirmedVisitTimesByDate(localDate);
    }


}
