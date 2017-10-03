package dentist.office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "patient/index";
    }

    @RequestMapping("/doctor")
    public String doctor() {
        return "doctor/doctor";
    }

}
