package airtrafficcontrol.service;

import airtrafficcontrol.exception.BadRequestException;
import airtrafficcontrol.model.AirCraft;
import airtrafficcontrol.model.QueueEntry;
import com.google.common.collect.ImmutableSet;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An in-memory implementation of {@link AirTrafficService} that uses an underlying {@link TreeSet} to
 * save/retrieve entries.
 */
@Component
public class InMemoryAirTrafficService implements AirTrafficService {

    private SortedSet<QueueEntry> queue;

    public InMemoryAirTrafficService() {
        initialize();
    }

    @Override
    public boolean reset() {
        initialize();
        return true;
    }

    @Override
    public QueueEntry enqueue(AirCraft airCraft) {
        QueueEntry entry = new QueueEntry(airCraft, DateTime.now());
        queue.add(entry);
        return entry;
    }

    @Override
    public QueueEntry dequeue() {
        if (queue.isEmpty()) {
            throw new BadRequestException("Cannot perform a deqeue operation because the queue is empty.", null);
        }
        QueueEntry first = queue.first();
        queue.remove(first);
        return first;
    }

    @Override
    public Set<QueueEntry> queue() {
        return ImmutableSet.copyOf(queue);
    }

    @Override
    public int queueSize() {
        return queue.size();
    }

    private void initialize() {
        this.queue = new TreeSet<>();
    }
}
