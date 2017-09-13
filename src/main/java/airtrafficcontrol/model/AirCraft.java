package airtrafficcontrol.model;

import java.util.Objects;

/**
 * Model object representing an aircraft.
 */
public class AirCraft {

    private AirCraftType type;

    private AirCraftSize size;

    public AirCraft() {
        // We only need this to enable Jackson deserialization. It's not actually used in the code.
    }

    public AirCraft(AirCraftType type, AirCraftSize size) {
        this.type = type;
        this.size = size;
    }

    public AirCraftType getType() {
        return type;
    }

    public AirCraftSize getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if ((o == null) || getClass() != o.getClass()) {
            return false;
        }
        AirCraft other = (AirCraft) o;

        // compare fields
        return Objects.equals(type, other.getType())
                && Objects.equals(size, other.getSize());
    }
}
