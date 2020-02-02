package frc.team3388.time;

import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;

public class NtpServer {
    private final NetworkTableEntry clientEntry;
    private final NetworkTableEntry serverRecTimeEntry;
    private final NetworkTableEntry serverSendTimeEntry;
    private final Clock clock;

    public NtpServer(NetworkTableEntry clientEntry, NetworkTableEntry serverRecTimeEntry, NetworkTableEntry serverSendTimeEntry, Clock clock) {
        this.clientEntry = clientEntry;
        this.clientEntry.setBoolean(false);

        this.serverSendTimeEntry = serverSendTimeEntry;
        this.serverSendTimeEntry.setDouble(0.0);

        this.serverRecTimeEntry = serverRecTimeEntry;
        this.serverRecTimeEntry.setDouble(0.0);

        this.clock = clock;

        this.clientEntry.addListener(this::onClientRequest, EntryListenerFlags.kUpdate);
    }

    private void onClientRequest(EntryNotification notification) {
        if (!notification.value.getBoolean()) {
            return;
        }

        long receiveTimestamp = clock.currentTime().valueAsMillis();
        serverRecTimeEntry.setDouble(receiveTimestamp);

        long sendTimestamp = clock.currentTime().valueAsMillis();
        serverSendTimeEntry.setDouble(sendTimestamp);

        clientEntry.setBoolean(false);
    }

}
