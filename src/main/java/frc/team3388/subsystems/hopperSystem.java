package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedController;

public class hopperSystem extends ConstantSpeedRotatableSubsystem {
    private static final int CONTROLLER_PORT = 9;
    private static final double SPEED = 0.8;

    public hopperSystem(SpeedController controller) {
        super(controller, SPEED);
    }

    public static hopperSystem forRobot() {
        WPI_VictorSPX controller = new WPI_VictorSPX(CONTROLLER_PORT); controller.setInverted(true);
        return new hopperSystem(controller);
    }
}
