package airtrafficcontrol;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class AirTrafficController {
    
    @RequestMapping("/")
    public String index() {
        return "Welcome to the Air Traffic Controller!";
    }
    
}
