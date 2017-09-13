package airtrafficcontrol.service;

import airtrafficcontrol.model.AirCraft;
import airtrafficcontrol.model.QueueEntry;

import java.util.Set;

/**
 * This is the interface for the service level class that performs request operations for the
 * air traffic control system. If necessary, this interface could be implemented to read from and write to
 * a persistent database.
 * Currently the only implementation, {@link InMemoryAirTrafficService}, is an in-memory implementation that
 * saves entries into a set.
 */
public interface AirTrafficService {

    boolean reset();

    QueueEntry enqueue(AirCraft airCraft);

    QueueEntry dequeue();

    Set<QueueEntry> queue();

    int queueSize();

}
