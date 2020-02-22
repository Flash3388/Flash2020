package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.objects.NetworkDoubleSupplier;

public class VisionSystem extends Subsystem {
    private static final String CAMERA_CONTROL_TABLE_NAME = "cameraCtrl";
    private static final String EXPOSURE_ENTRY_NAME = "exposure";
    private static final String VISION_TABLE_NAME = "vision";
    private static final String ALIGNMENT_ERROR_ENTRY_NAME = "angle_degrees";
    private static final String DISTANCE_ENTRY_NAME = "distance_cm";
    private static final int PROCESSING_EXPOSURE_VALUE = 10;
    private static final int DEFAULT_EXPOSURE_VALUE = 46;

    private final NetworkTableEntry exposureEntry;
    private final NetworkDoubleSupplier alignmentErrorSupplier;
    private final NetworkDoubleSupplier distanceSupplier;

    public VisionSystem(NetworkTableEntry exposureEntry, NetworkDoubleSupplier alignmentErrorSupplier, NetworkDoubleSupplier distanceSupplier) {
        this.exposureEntry = exposureEntry;
        this.alignmentErrorSupplier = alignmentErrorSupplier;
        this.distanceSupplier = distanceSupplier;
    }

    public static VisionSystem forRobot() {
        NetworkTableEntry exposureEntry = NetworkTableInstance.getDefault().getTable(CAMERA_CONTROL_TABLE_NAME).getEntry(EXPOSURE_ENTRY_NAME);
        exposureEntry.setDouble(DEFAULT_EXPOSURE_VALUE);
        NetworkDoubleSupplier alignmentErrorSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, ALIGNMENT_ERROR_ENTRY_NAME, 0);
        NetworkDoubleSupplier distanceSupplier = new NetworkDoubleSupplier(VISION_TABLE_NAME, DISTANCE_ENTRY_NAME, -1);

        return new VisionSystem(exposureEntry, alignmentErrorSupplier, distanceSupplier);
    }

    public void setProcessingMode() {
        exposureEntry.setDouble(PROCESSING_EXPOSURE_VALUE);
    }

    public void setNormalMode() {
        exposureEntry.setDouble(DEFAULT_EXPOSURE_VALUE);
    }

    public double alignmentError() {
        return alignmentErrorSupplier.getAsDouble();
    }

    public double distance() {
        return distanceSupplier.getAsDouble();
    }

    public boolean hasFoundTarget() {
        return distanceSupplier.getAsDouble() == -1;
    }
}
