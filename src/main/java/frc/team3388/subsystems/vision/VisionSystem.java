package frc.team3388.subsystems.vision;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.time.Time;

public class VisionSystem extends Subsystem {
    private static final String VISION_TABLE_NAME = "vision_table";
    private static final String ALIGNMENT_ERROR_ENTRY_NAME = "alignment_error";
    private static final String DISTANCE_ENTRY_NAME = "distance";
    private static final String TIMESTAMP_ENTRY_NAME = "timestamp_milliseconds";

    private final NetworkDoubleSupplier alignmentErrorSupplier;
    private final NetworkDoubleSupplier distanceSupplier;
    private final NetworkIntSupplier timestampMillisecondsSupplier;

    public VisionSystem() {
        alignmentErrorSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, ALIGNMENT_ERROR_ENTRY_NAME, 0);
        distanceSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, DISTANCE_ENTRY_NAME, -1);
        timestampMillisecondsSupplier = new NetworkIntSupplier(VISION_TABLE_NAME, TIMESTAMP_ENTRY_NAME, -1);
    }

    public double alignmentError() {
        return alignmentErrorSupplier.getAsDouble();
    }

    public double distance() {
        return distanceSupplier.getAsDouble();
    }

    public Time timestamp() {
        return Time.milliseconds(timestampMillisecondsSupplier.getAsInt());
    }
}
