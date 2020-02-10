package frc.team3388.subsystems.storage;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import edu.wpi.first.wpilibj.SpeedController;

public class ShooterFeederSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 2;
    private static final double SPEED = 0.5;

    public ShooterFeederSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static ShooterFeederSystem forRobot() {
        return new ShooterFeederSystem(new WPI_VictorSPX(CONTROLLER_PORT));
    }
}
