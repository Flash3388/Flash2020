package frc.team3388.subsystems.vision;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.function.IntSupplier;

public class NetworkIntSupplier implements IntSupplier {
    private final NetworkTableEntry entry;
    private final int defaultValue;

    public NetworkIntSupplier(String tableName, String entryName, int defaultValue) {
        this.defaultValue = defaultValue;
        entry = NetworkTableInstance.getDefault().getTable(tableName).getEntry(entryName);
        entry.setDouble(defaultValue);
    }

    @Override
    public int getAsInt() {
        return (int) entry.getDouble(defaultValue);
    }
}
