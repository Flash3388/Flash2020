package frc.team3388.subsystems;

import com.beans.DoubleProperty;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NetworkDoubleProperty implements DoubleProperty {
    private final NetworkTableEntry entry;

    public NetworkDoubleProperty(NetworkTableEntry entry) {
        this.entry = entry;
        entry.setDouble(0);
    }

    @Override
    public double getAsDouble() {
        return entry.getDouble(0);
    }

    @Override
    public void setAsDouble(double value) {
        entry.setDouble(value);
    }

    @Override
    public Double get() {
        return entry.getDouble(0);
    }

    @Override
    public void set(Double value) {

    }
}
