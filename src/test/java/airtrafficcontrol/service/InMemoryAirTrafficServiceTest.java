package airtrafficcontrol.service;

import airtrafficcontrol.exception.BadRequestException;
import airtrafficcontrol.model.AirCraft;
import airtrafficcontrol.model.AirCraftSize;
import airtrafficcontrol.model.AirCraftType;
import airtrafficcontrol.model.QueueEntry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;

import static org.junit.Assert.*;

public class InMemoryAirTrafficServiceTest {

    private AirTrafficService service;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static final AirCraft LARGE_PASSENGER = new AirCraft(AirCraftType.PASSENGER, AirCraftSize.LARGE);
    private static final AirCraft SMALL_PASSENGER = new AirCraft(AirCraftType.PASSENGER, AirCraftSize.SMALL);
    private static final AirCraft LARGE_CARGO = new AirCraft(AirCraftType.CARGO, AirCraftSize.LARGE);
    private static final AirCraft SMALL_CARGO = new AirCraft(AirCraftType.CARGO, AirCraftSize.SMALL);

    @Before
    public void setUp() throws Exception {
        this.service = new InMemoryAirTrafficService();
    }

    @Test
    public void testConstructor() throws Exception {
        assertQueueSize(0);
    }

    @Test
    public void testResetWithEmptyQueue() throws Exception {
        service.reset();
        assertQueueSize(0);
    }

    @Test
    public void testResetAfterEnqueue() throws Exception {
        service.enqueue(SMALL_CARGO);
        assertQueueSize(1);
        service.reset();
        assertQueueSize(0);
    }

    @Test
    public void testResetAfterDequeue() throws Exception {
        service.enqueue(SMALL_CARGO);
        service.enqueue(LARGE_PASSENGER);
        service.enqueue(LARGE_CARGO);
        assertQueueSize(3);
        service.dequeue();
        assertQueueSize(2);
        service.reset();
        assertQueueSize(0);
    }

    @Test
    public void testEnqueueOrder() throws Exception {
        // Add everything type of aircraft to verify priority in the queue.
        QueueEntry smallCargo1 = service.enqueue(SMALL_CARGO);
        QueueEntry largePassenger1 = service.enqueue(LARGE_PASSENGER);
        QueueEntry largeCargo1 = service.enqueue(LARGE_CARGO);
        QueueEntry smallPassenger1 = service.enqueue(SMALL_PASSENGER);

        // Manual pause to make certain the time stamps are different between the first batch of four and the second
        Thread.sleep(100);

        QueueEntry largeCargo2 = service.enqueue(LARGE_CARGO);
        QueueEntry smallPassenger2 = service.enqueue(SMALL_PASSENGER);
        QueueEntry largePassenger2 = service.enqueue(LARGE_PASSENGER);
        QueueEntry smallCargo2 = service.enqueue(SMALL_CARGO);

        assertQueueSize(8);
        dequeueAndVerify(largePassenger1);
        dequeueAndVerify(largePassenger2);
        dequeueAndVerify(smallPassenger1);
        dequeueAndVerify(smallPassenger2);
        dequeueAndVerify(largeCargo1);
        dequeueAndVerify(largeCargo2);
        dequeueAndVerify(smallCargo1);
        dequeueAndVerify(smallCargo2);
        assertQueueSize(0);
    }

    @Test
    public void testDequeue() throws Exception {
        QueueEntry smallCargo = service.enqueue(SMALL_CARGO);
        QueueEntry largePassenger = service.enqueue(LARGE_PASSENGER);
        QueueEntry largeCargo = service.enqueue(LARGE_CARGO);

        assertQueueSize(3);
        dequeueAndVerify(largePassenger);
        dequeueAndVerify(largeCargo);
        dequeueAndVerify(smallCargo);
        assertQueueSize(0);
    }

    @Test
    public void testDequeueEmpty() throws Exception {
        exception.expect(BadRequestException.class);
        exception.expectMessage("Cannot perform a deqeue operation because the queue is empty");

        service.dequeue();
    }

    @Test
    public void testQueueOrder() throws Exception {
        // Add everything type of aircraft to verify priority in the queue.
        QueueEntry smallCargo1 = service.enqueue(SMALL_CARGO);
        QueueEntry largePassenger1 = service.enqueue(LARGE_PASSENGER);
        QueueEntry largeCargo1 = service.enqueue(LARGE_CARGO);
        QueueEntry smallPassenger1 = service.enqueue(SMALL_PASSENGER);

        // Manual pause to make certain the time stamps are different between the first batch of four and the second
        Thread.sleep(100);

        QueueEntry largeCargo2 = service.enqueue(LARGE_CARGO);
        QueueEntry smallPassenger2 = service.enqueue(SMALL_PASSENGER);
        QueueEntry largePassenger2 = service.enqueue(LARGE_PASSENGER);
        QueueEntry smallCargo2 = service.enqueue(SMALL_CARGO);

        Set<QueueEntry> queue = service.queue();
        QueueEntry[] queueArray = queue.stream().toArray(QueueEntry[]::new);
        QueueEntry[] expected = new QueueEntry[] {
                largePassenger1,
                largePassenger2,
                smallPassenger1,
                smallPassenger2,
                largeCargo1,
                largeCargo2,
                smallCargo1,
                smallCargo2,
        };
        assertArrayEquals("Expected the two arrays to be the same.", expected, queueArray);
    }

    @Test
    public void queueSizeInitial() throws Exception {
        assertQueueSize(0);
    }

    @Test
    public void queueSizeAfterEnqueue() throws Exception {
        service.enqueue(LARGE_CARGO);
        service.enqueue(LARGE_PASSENGER);
        assertQueueSize(2);
    }

    @Test
    public void queueSizeAfterDqueue() throws Exception {
        service.enqueue(LARGE_CARGO);
        service.enqueue(LARGE_PASSENGER);
        assertQueueSize(2);
        service.dequeue();
        assertQueueSize(1);
        service.dequeue();
        assertQueueSize(0);
    }

    private void assertQueueSize(int expecetd) throws Exception {
        if (expecetd == 0) {
            assertEquals("Expected queue to be empty.", expecetd, this.service.queueSize());
        } else {
            assertEquals("Expected queue to have " + expecetd + " elements.", expecetd, this.service.queueSize());
        }
    }

    private void dequeueAndVerify(QueueEntry expected) {
        QueueEntry dequeueResult = service.dequeue();
        assertSame("Unexpected item returned.", expected, dequeueResult);
    }

}