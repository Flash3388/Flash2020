package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class ClimbSystem extends Subsystem {
    private static final int LIFT_CONTROLLER_PORT = 4;
    private static final int HOOK_CONTROLLER_PORT = 5;
    private static final double HOOK_LIFT_SPEED = 0.3;
    private static final double LIFT_SPEED = 1;

    private SpeedController liftController;
    private SpeedController hookController;

    public ClimbSystem(SpeedController liftController , SpeedController hookController) {
        this.liftController = liftController;
        this.hookController = hookController;
    }

    public static ClimbSystem forRobot() {
        WPI_TalonSRX liftController = new WPI_TalonSRX(LIFT_CONTROLLER_PORT);
        WPI_TalonSRX hookController = new WPI_TalonSRX(HOOK_CONTROLLER_PORT);

        return new ClimbSystem(liftController, hookController);
    }

    public void lift() {
        liftController.set(LIFT_SPEED);
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
