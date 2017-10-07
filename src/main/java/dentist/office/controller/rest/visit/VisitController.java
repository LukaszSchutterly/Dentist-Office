package dentist.office.controller.rest.visit;

import dentist.office.model.entity.visit.Visit;
import dentist.office.service.entity.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitController {

    private VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postVisit(@Valid @RequestBody Visit visit) {
        visit.setConfirmed(true);
        visit.setAccepted(true);

        visitService.saveOrUpdate(visit);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void putVisit(@Valid @RequestBody Visit visit) {
        visitService.saveOrUpdate(visit);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteVisit(@PathVariable Long id) {
        visitService.removeById(id);
    }

    @GetMapping(value = "/byAccepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Visit> getAllVisitsByAccepted(@RequestParam boolean accepted) {
        return visitService.getConfirmedVisitsByAccepted(accepted);
    }

    @GetMapping(value = "/byDate/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Visit> getConfirmedVisitsByDate(@PathVariable LocalDate date) {
        return visitService.getVisitsByDateAndConfirmed(date, true);
    }

    @GetMapping(value = "/byPatient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Visit> getConfirmedVisitsByPatientOrderedByDate(@PathVariable Long id) {
        return visitService.getVisitsByPatientIdAndConfirmedOrderedByDate(id, true);
    }

}
