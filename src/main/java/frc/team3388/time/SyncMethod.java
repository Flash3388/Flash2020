package frc.team3388.time;

import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SyncMethod {
    private static final String SYNC_TABLE_NAME = "sync";
    private static final String SYNC_ENTRY_NAME = "should";
    private static final String FIRST_TIMESTAMP_ENTRY_NAME = "first_timestamp_millis";
    private static final String SECOND_TIMESTAMP_ENTRY_NAME = "second_timestamp_millis";

    private final NetworkTableEntry syncEntry;
    private final NetworkTableEntry firstTimestampEntry;
    private final NetworkTableEntry secondTimestampEntry;
    private final Clock clock;

    public SyncMethod(NetworkTableEntry syncEntry, NetworkTableEntry firstTimestampEntry, NetworkTableEntry secondTimestampEntry, Clock clock) {
        this.syncEntry = syncEntry;
        this.firstTimestampEntry = firstTimestampEntry;
        this.secondTimestampEntry = secondTimestampEntry;
        this.clock = clock;

        syncEntry.setBoolean(false);
        firstTimestampEntry.setDouble(-1);
        secondTimestampEntry.setDouble(-1);
    }

    public static SyncMethod standard(Clock clock) {
        NetworkTable syncTable = NetworkTableInstance.getDefault().getTable(SYNC_TABLE_NAME);
        return new SyncMethod(syncTable.getEntry(SYNC_ENTRY_NAME), syncTable.getEntry(FIRST_TIMESTAMP_ENTRY_NAME), syncTable.getEntry(SECOND_TIMESTAMP_ENTRY_NAME), clock);
    }

    public boolean shouldSync() {
        return syncEntry.getBoolean(false);
    }

    public void setFirst() {
        firstTimestampEntry.setDouble(clock.currentTime().valueAsMillis());
    }

    public void setSecond() {
        secondTimestampEntry.setDouble(clock.currentTime().valueAsMillis());
    }

    public void unSync() {
        syncEntry.setBoolean(false);
    }
}
