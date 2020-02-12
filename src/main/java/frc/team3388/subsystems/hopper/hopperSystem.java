package frc.team3388.subsystems.hopper;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.subsystems.ConstantSpeedRotatableSubsystem;

public class hopperSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 1;
    private static final double SPEED = 0.8;

    public hopperSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static hopperSystem forRobot() {
        return new hopperSystem(new WPI_VictorSPX(CONTROLLER_PORT));
    }
}
