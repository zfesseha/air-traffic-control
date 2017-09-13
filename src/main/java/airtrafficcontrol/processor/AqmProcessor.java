package airtrafficcontrol.processor;

import airtrafficcontrol.exception.BadRequestException;
import airtrafficcontrol.model.AirCraft;
import airtrafficcontrol.model.QueueEntry;
import airtrafficcontrol.processor.dto.AqmRequest;
import airtrafficcontrol.processor.dto.AqmResponse;
import airtrafficcontrol.service.AirTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * This class is what's called by the API to perform various operations. The main method of this class,
 * {@link #aqmRequestProcess(AqmRequest)}, takes a request object, determines what type of request it is,
 * and pass it along to the underlying {@link AirTrafficService} to perform the operation.
 * It also includes a {@link #queue()} method to retrieve the current queue from the server.
 */
@Component
public class AqmProcessor {

    @Autowired
    private AirTrafficService airTrafficService;

    public AqmProcessor(AirTrafficService service) {
        this.airTrafficService = service;
    }

    public AqmResponse aqmRequestProcess(AqmRequest request) {

        try {
            switch (request.getType()) {
                case START:
                    return start();
                case ENQUEUE:
                    return enqueue(request.getAirCraft());
                case DEQUEUE:
                    return dequeue();
                default:
                    // This should never happen.
                    return AqmResponse.failure(null, "Unrecognized command.");
            }
        } catch (BadRequestException e) {
            return AqmResponse.failure(null, e.getMessage());
        } catch (Exception e) {
            return AqmResponse.failure(null, "The server could not process your request.");
        }
    }

    public Set<QueueEntry> queue() {
        return airTrafficService.queue();
    }

    private AqmResponse start() {
        boolean result = airTrafficService.reset();
        return result ? AqmResponse.success() : AqmResponse.failure(null, "The Air Traffic Queue System could not be started.");
    }

    private AqmResponse enqueue(AirCraft airCraft) {
        QueueEntry result = airTrafficService.enqueue(airCraft);
        if (result != null) {
            return AqmResponse.success(result);
        } else {
            return AqmResponse.failure(result, "The provided aircraft could not be added to the queue.");
        }
    }

    private AqmResponse dequeue() {
        int planesInQueue = airTrafficService.queueSize();
        if (planesInQueue <= 0) {
            return AqmResponse.failure(null, "There are no aircrafts in the queue.");
        }
        QueueEntry result = airTrafficService.dequeue();
        if (result != null) {
            return AqmResponse.success(result);
        } else {
            return AqmResponse.failure(result, "Attempt to retrieve the next aircraft was unsuccessful.");
        }
    }
}
