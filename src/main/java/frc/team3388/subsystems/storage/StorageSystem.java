package frc.team3388.subsystems.storage;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import edu.wpi.first.wpilibj.SpeedController;

public class StorageSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 1;
    private static final double SPEED = -0.4;

    public StorageSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static StorageSystem forRobot() {
        return new StorageSystem(new WPI_VictorSPX(CONTROLLER_PORT));
    }
}
