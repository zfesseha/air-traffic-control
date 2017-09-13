package airtrafficcontrol.controller;

import airtrafficcontrol.model.QueueEntry;
import airtrafficcontrol.processor.AqmProcessor;
import airtrafficcontrol.processor.dto.AqmRequest;
import airtrafficcontrol.processor.dto.AqmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * This is the main controller for the RESTful aspect of this application. It defines all the REST endpoints
 * for this application. All the endpoints are described in the README.md.
 */
@RestController
@RequestMapping("/api/v1/queue")
public class AirTrafficController {

    @Autowired
    private AqmProcessor processor;

    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public AqmResponse start() {
        return processor.aqmRequestProcess(AqmRequest.start());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<QueueEntry> queue() {
        return processor.queue();
    }

    @RequestMapping(method = RequestMethod.POST)
    public AqmResponse process(@RequestBody AqmRequest request) {
        AqmResponse aqmResponse = processor.aqmRequestProcess(request);
        return aqmResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dequeue")
    public AqmResponse dequeue() {
        AqmResponse aqmResponse = processor.aqmRequestProcess(AqmRequest.dequeue());
        return aqmResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/help")
    public String index() {
        return "Welcome to the Air Traffic Controller!";
    }

}
