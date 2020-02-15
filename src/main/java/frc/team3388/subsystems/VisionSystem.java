package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import frc.team3388.objects.NetworkDoubleSupplier;

public class VisionSystem extends Subsystem {
    private static final String VISION_TABLE_NAME = "vision";
    private static final String ALIGNMENT_ERROR_ENTRY_NAME = "angle_degrees";
    private static final String DISTANCE_ENTRY_NAME = "distance_cm";

    private final NetworkDoubleSupplier alignmentErrorSupplier;
    private final NetworkDoubleSupplier distanceSupplier;

    public VisionSystem(NetworkDoubleSupplier alignmentErrorSupplier, NetworkDoubleSupplier distanceSupplier) {
        this.alignmentErrorSupplier = alignmentErrorSupplier;
        this.distanceSupplier = distanceSupplier;
    }

    public static VisionSystem forRobot() {
        NetworkDoubleSupplier alignmentErrorSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, ALIGNMENT_ERROR_ENTRY_NAME, 0);
        NetworkDoubleSupplier distanceSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, DISTANCE_ENTRY_NAME, -1);
        return new VisionSystem(alignmentErrorSupplier, distanceSupplier);
    }

    public double alignmentError() {
        return alignmentErrorSupplier.getAsDouble();
    }

    public double distance() {
        return distanceSupplier.getAsDouble();
    }
}
