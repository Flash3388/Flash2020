package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class ClimbSystem extends Subsystem {
    private static final double HOOK_LIFT_SPEED = 0.3;
    private static final double LIFT_SPEED = 1;

    private SpeedController liftController;
    private SpeedController hookController;

    public ClimbSystem(SpeedController liftController , SpeedController hookController) {
        this.liftController = liftController;
        this.hookController = hookController;
    }

    public void lift() {
        liftController.set(LIFT_SPEED);
    }

    public void lower() {
        liftController.set(-LIFT_SPEED);
    }

    public void liftHook() {
        hookController.set(HOOK_LIFT_SPEED);
    }

    public void stopLift() {
        liftController.stopMotor();
    }

    public void stopHookLift() {
        hookController.stopMotor();
    }
}
