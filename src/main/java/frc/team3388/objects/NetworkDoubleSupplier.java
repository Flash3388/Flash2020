package frc.team3388.objects;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.function.DoubleSupplier;

public class NetworkDoubleSupplier implements DoubleSupplier {
    private final NetworkTableEntry entry;
    private final double defaultValue;

    public NetworkDoubleSupplier(String tableName, String entryName, double defaultValue) {
        this.defaultValue = defaultValue;
        entry = NetworkTableInstance.getDefault().getTable(tableName).getEntry(entryName);
        entry.setDouble(defaultValue);
    }

    @Override
    public double getAsDouble() {
        return entry.getDouble(defaultValue);
    }
}
