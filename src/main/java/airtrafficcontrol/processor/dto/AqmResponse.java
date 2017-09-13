package airtrafficcontrol.processor.dto;

import airtrafficcontrol.model.QueueEntry;

/**
 * A simple class that represents a response to the system. It provides static helpers to easily
 * construct response objects.
 */
public class AqmResponse {

    private boolean isSuccessful;

    private QueueEntry entry;

    private String message;

    public AqmResponse(boolean isSuccessful, QueueEntry entry, String message) {
        this.isSuccessful = isSuccessful;
        this.entry = entry;
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public QueueEntry getEntry() {
        return entry;
    }

    public String getMessage() {
        return message;
    }

    public static AqmResponse success() {
        return success(null);
    }

    public static AqmResponse success(QueueEntry entry) {
        return new AqmResponse(true, entry, null);
    }

    public static AqmResponse failure(QueueEntry entry, String message) {
        return new AqmResponse(false, entry, message);
    }
}
