package frc.team3388.actions.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.jmath.ExtendedMath;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TurretSystem extends Subsystem implements Rotatable {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 360;
    private static final double SPEED = 0.1;

    private final SpeedController controller;
    private final PidController pidController;
    private final Gyro encoder;
    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController pidController, Gyro gyro, double maxAngle) {
        this.controller = controller;
        this.pidController = pidController;
        this.encoder = gyro;
        this.maxAngle = maxAngle;
    }

    public TurretSystem forRobot() {
        final double kP = 0.1;
        final double kI = 0;
        final double kD = 0;

        return new TurretSystem(new WPI_TalonSRX(CONTROLLER_PORT), new PidController(kP, kI, kD, 0), null, DEFAULT_MAX_ANGLE);
    }

    public void turnRight() {
        rotate(SPEED);
    }

    public void turnLeft() {
        rotate(-SPEED);
    }

    public double getAngle() {
        return encoder.getAngle();
    }

    public void rotateTo(double target) {
        rotate(pidController.calculate(getAngle(), target));
    }

    @Override
    public void rotate(double speed) {
        if(canRotate())
            controller.set(speed);
    }

    @Override
    public void stop() {
        controller.stopMotor();
    }

    private boolean canRotate() {
        return ExtendedMath.constrained(getAngle(), -maxAngle, maxAngle);
    }
}
