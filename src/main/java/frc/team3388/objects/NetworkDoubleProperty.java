package frc.team3388.objects;

import com.beans.DoubleProperty;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkDoubleProperty implements DoubleProperty {
    private final NetworkTableEntry entry;
    private final double defaultValue;

    public NetworkDoubleProperty(String tableName, String entryName, double defaultValue) {
        this(NetworkTableInstance.getDefault().getTable(tableName).getEntry(entryName), defaultValue);
    }

    public NetworkDoubleProperty(NetworkTableEntry entry, double defaultValue) {
        this.entry = entry;
        this.defaultValue = defaultValue;
        entry.setDouble(defaultValue);
    }

    @Override
    public double getAsDouble() {
        return entry.getDouble(defaultValue);
    }

    @Override
    public void setAsDouble(double value) {
        entry.setDouble(value);
    }

    @Override
    public Double get() {
        return entry.getDouble(defaultValue);
    }

    @Override
    public void set(Double value) {

    }
}
