package frc.team3388.subsystems;

import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem {
    private static final int FIRST_CONTROLLER_PORT = 0;
    private static final int SECOND_CONTROLLER_PORT = 1;
    private static final double ROTATE_SPEED = 0.3;

    public ClimbSystem(SpeedController controller) {
        super(controller, ROTATE_SPEED);
    }

    public static ClimbSystem forRobot() {
        PWMVictorSPX first = new PWMVictorSPX(FIRST_CONTROLLER_PORT);
        PWMVictorSPX second = new PWMVictorSPX(SECOND_CONTROLLER_PORT);

        return new ClimbSystem(new SpeedControllerGroup(first, second));
    }
}
