package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;

public class ShooterFeederSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 9;
    private static final double SPEED = 0.3;

    public ShooterFeederSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static ShooterFeederSystem forRobot() {
        WPI_VictorSPX controller = new WPI_VictorSPX(CONTROLLER_PORT); controller.setInverted(true);
        return new ShooterFeederSystem(controller);
    }
}
