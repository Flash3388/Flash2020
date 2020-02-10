package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem{
    private static final int CONTROLLER_PORT = 4;
    private static final double SPEED = 1;

    public ClimbSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static ClimbSystem forRobot() {
        WPI_TalonSRX liftController = new WPI_TalonSRX(CONTROLLER_PORT);

        return new ClimbSystem(liftController);
    }
}
