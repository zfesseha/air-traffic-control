package airtrafficcontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is the main controller for the web application. It only maps the root route to a simple
 * index.html template. Everything else in the frontend is handled by React.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
