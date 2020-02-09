package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.jmath.ExtendedMath;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.SrxEncoder;

public class TurretSystem extends Subsystem implements Rotatable {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 360;
    private static final int LARGE_GEAR_TOOTH_COUNT = 100;
    private static final int LITTLE_GEAR_TOOTH_COUNT = 10;
    private static final double PID_LIMIT = 0.4;
    private static final double DEFAULT_DELTA = 0.5;

    private final SpeedController controller;
    private final PidController pidController;
    private final SrxEncoder encoder;
    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController pidController, SrxEncoder encoder, double maxAngle) {
        this.controller = controller;
        this.pidController = pidController;
        this.encoder = encoder;
        this.maxAngle = maxAngle;

        pidController.setOutputLimit(PID_LIMIT);
    }

    public TurretSystem forRobot() {
        final double kP = 0.1;
        final double kI = 0;
        final double kD = 0;

        PidController pidController = new PidController(kP, kI, kD, 0);
        WPI_TalonSRX talon = new WPI_TalonSRX(CONTROLLER_PORT);
        SrxEncoder encoder = new SrxEncoder(CONTROLLER_PORT, LITTLE_GEAR_TOOTH_COUNT/(double)LARGE_GEAR_TOOTH_COUNT);

        return new TurretSystem(talon, pidController, encoder, DEFAULT_MAX_ANGLE);
    }

    public void resetPidController() {
        pidController.reset();
    }

    public double angle() {
        return encoder.getAsDouble();
    }

    public void rotateTo(double target) {
        rotate(pidController.calculate(angle(), target));
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

    public boolean hasReachedTarget(double target) {
        return ExtendedMath.equals(angle(), target, DEFAULT_DELTA);
    }

    private boolean canRotate() {
        return ExtendedMath.constrained(angle(), -maxAngle, maxAngle);
    }
}
