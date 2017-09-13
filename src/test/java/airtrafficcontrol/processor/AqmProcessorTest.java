package airtrafficcontrol.processor;

import airtrafficcontrol.model.AirCraft;
import airtrafficcontrol.model.AirCraftSize;
import airtrafficcontrol.model.AirCraftType;
import airtrafficcontrol.model.QueueEntry;
import airtrafficcontrol.processor.dto.AqmRequest;
import airtrafficcontrol.processor.dto.AqmResponse;
import airtrafficcontrol.service.InMemoryAirTrafficService;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This test doesn't cover the various combinations of aircrafts to verify the order in which items are
 * inserted. That logic is covered in {@link InMemoryAirTrafficService}. This test covers the happy path
 * scenarios for each operation and includes the test scenarios not covered in the other test.
 */
public class AqmProcessorTest {

    private AqmProcessor processor;

    private static final AirCraft LARGE_PASSENGER = new AirCraft(AirCraftType.PASSENGER, AirCraftSize.LARGE);
    private static final AirCraft SMALL_PASSENGER = new AirCraft(AirCraftType.PASSENGER, AirCraftSize.SMALL);
    private static final AirCraft LARGE_CARGO = new AirCraft(AirCraftType.CARGO, AirCraftSize.LARGE);
    private static final AirCraft SMALL_CARGO = new AirCraft(AirCraftType.CARGO, AirCraftSize.SMALL);

    @Before
    public void setUp() throws Exception {
        processor = new AqmProcessor(new InMemoryAirTrafficService());
    }

    @Test
    public void aqmRequestProcess() throws Exception {
    }

    @Test
    public void testQueueEmpty() throws Exception {
        Set<QueueEntry> queue = processor.queue();
        QueueEntry[] queueArray = queue.stream().toArray(QueueEntry[]::new);
        assertArrayEquals("Expected the two arrays to be the same.", new QueueEntry[] {}, queueArray);
    }

    @Test
    public void testEnqueue() throws Exception {
        Set<QueueEntry> queue = processor.queue();
        AqmResponse aqmResponse = processor.aqmRequestProcess(AqmRequest.enqueue(SMALL_PASSENGER));

        QueueEntry[] queueArray = queue.stream().toArray(QueueEntry[]::new);
        QueueEntry[] expected = new QueueEntry[] {
                aqmResponse.getEntry()
        };
        assertArrayEquals("Expected the two arrays to be the same.", new QueueEntry[] {}, queueArray);
    }

    @Test
    public void testDequeue() throws Exception {
        AqmResponse smallPassengerResponse = processor.aqmRequestProcess(AqmRequest.enqueue(SMALL_PASSENGER));
        AqmResponse largeCargoResponse = processor.aqmRequestProcess(AqmRequest.enqueue(LARGE_CARGO));

        AqmResponse dequeueResult = processor.aqmRequestProcess(AqmRequest.dequeue());
        assertEquals("Expected the result to be successful.", true, dequeueResult.isSuccessful());
        assertEquals("Expected the message in the response to be null.", null, dequeueResult.getMessage());
        assertEquals("Expected to dequeue a different entry.", smallPassengerResponse.getEntry(), dequeueResult.getEntry());

        Set<QueueEntry> queue = processor.queue();
        QueueEntry[] queueArray = queue.stream().toArray(QueueEntry[]::new);
        QueueEntry[] expected = new QueueEntry[] {
                largeCargoResponse.getEntry()
        };
        assertArrayEquals("Expected the two arrays to be the same.", expected, queueArray);
    }

    @Test
    public void testDequeueEmpty() throws Exception {
        AqmResponse dequeueResult = processor.aqmRequestProcess(AqmRequest.dequeue());

        Set<QueueEntry> queue = processor.queue();
        QueueEntry[] queueArray = queue.stream().toArray(QueueEntry[]::new);
        QueueEntry[] expected = new QueueEntry[] {
        };
        assertArrayEquals("Expected the two arrays to be the same.", expected, queueArray);
        assertFalse("Expected the result to be successful.", dequeueResult.isSuccessful());
        assertEquals("Expected the message in the response to be different.", "There are no aircrafts in the queue.", dequeueResult.getMessage());
    }
}