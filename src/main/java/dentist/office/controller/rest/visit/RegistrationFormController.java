package dentist.office.controller.rest.visit;

import dentist.office.dto.RegistrationDTO;
import dentist.office.service.entity.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationFormController {

    private VisitService visitService;

    @Autowired
    public RegistrationFormController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registration(@Valid @RequestBody RegistrationDTO registrationDTO) {
        visitService.publicSave(registrationDTO.makeVisit());
    }

}
