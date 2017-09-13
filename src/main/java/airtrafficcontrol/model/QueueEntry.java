package airtrafficcontrol.model;

import org.joda.time.DateTime;

import java.util.Objects;

public class QueueEntry implements Comparable<QueueEntry> {

    private AirCraft airCraft;

    private DateTime requestTime;

    public QueueEntry(AirCraft airCraft, DateTime requestTime) {
        this.airCraft = airCraft;
        this.requestTime = requestTime;
    }

    public AirCraft getAirCraft() {
        return airCraft;
    }

    public DateTime getRequestTime() {
        return requestTime;
    }

    @Override
    public int compareTo(QueueEntry otherEntry) {int compare = getAirCraft().getType().compareTo(otherEntry.getAirCraft().getType());
        if (compare != 0) {
            return compare;
        }

        compare = getAirCraft().getSize().compareTo(otherEntry.getAirCraft().getSize());
        if (compare != 0) {
            return compare;
        }
        return requestTime.compareTo(otherEntry.requestTime);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if ((o == null) || getClass() != o.getClass()) {
            return false;
        }
        QueueEntry other = (QueueEntry) o;

        // compare fields
        return Objects.equals(airCraft, other.getAirCraft())
                && Objects.equals(requestTime, other.getRequestTime());
    }
}
