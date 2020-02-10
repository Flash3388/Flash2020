package frc.team3388.subsystems.storage;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.subsystems.ConstantSpeedRotatableSubsystem;

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
