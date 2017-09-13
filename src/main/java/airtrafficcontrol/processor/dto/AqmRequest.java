package airtrafficcontrol.processor.dto;

import airtrafficcontrol.exception.BadRequestException;
import airtrafficcontrol.model.AirCraft;

/**
 * A simple class that represents a request to the system. It provides static helpers to easily
 * construct request objects.
 */
public class AqmRequest {

    private AqmRequestType type;

    private AirCraft airCraft;

    public AqmRequest() {
        // We only need this to enable Jackson deserialization. It's not actually used in the code.
    }

    public AqmRequest(AqmRequestType type, AirCraft airCraft) {
        validate(type, airCraft);
        this.type = type;
        this.airCraft = airCraft;
    }

    public AqmRequestType getType() {
        return type;
    }

    public AirCraft getAirCraft() {
        return airCraft;
    }

    public static AqmRequest start() {
        return new AqmRequest(AqmRequestType.START, null);
    }

    public static AqmRequest enqueue(AirCraft airCraft) {
        return new AqmRequest(AqmRequestType.ENQUEUE, airCraft);
    }

    public static AqmRequest dequeue() {
        return new AqmRequest(AqmRequestType.DEQUEUE, null);
    }

    public void validate(AqmRequestType type, AirCraft airCraft) {
        if (type == null) {
            throw new BadRequestException(String.format("The request action cannot be null. It must be one of: [%s].", AqmRequestType.values()), null);
        }
        if (type == AqmRequestType.ENQUEUE && airCraft == null) {
            throw new BadRequestException("An aircraft must be specified for an ENQUEUE request.", null);
        }
    }
}
