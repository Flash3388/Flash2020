package frc.team3388.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.jmath.ExtendedMath;
import com.jmath.interpolation.Interpolation;
import com.jmath.interpolation.LagrangePolynomial;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class ShooterAngleAdjustmentSystem extends Subsystem implements Rotatable {
    private static final int CONTROLLER_PORT = 3;
    private static final int SENSOR_PORT = 0;
    private static final double REVERSE_MAX_VALUE = 1/4096.0;
    private static final double ANGLE_OFFSET = 100;
    private static final double VALUE_TO_ANGLE_RATION = 360;
    private static final double PID_LIMIT = 0.3;
    private static final Map<Double, Double> interpolationDataPoints = new HashMap<>();
    private static final double DEFAULT_MARGIN = 0.1;

    private final SpeedController controller;
    private final DoubleSupplier angleSupplier;
    private final PidController pidController;
    private final Interpolation interpolation;

    public ShooterAngleAdjustmentSystem(SpeedController controller, DoubleSupplier angleSupplier, PidController pidController, Interpolation interpolation) {
        this.controller = controller;
        this.angleSupplier = angleSupplier;
        this.pidController = pidController;
        this.interpolation = interpolation;
        pidController.setOutputLimit(PID_LIMIT);
    }

    public static ShooterAngleAdjustmentSystem forRobot() {
        final double kP = 0.1;
        final double kI = 0;
        final double kD = 0;

        PidController pidController =  new PidController(kP, kI, kD, 0);
        WPI_TalonSRX controller = new WPI_TalonSRX(CONTROLLER_PORT);
        DoubleSupplier angleSupplier = () -> new AnalogInput(SENSOR_PORT).getValue() * REVERSE_MAX_VALUE * VALUE_TO_ANGLE_RATION - ANGLE_OFFSET;
        Interpolation interpolation = LagrangePolynomial.fromMap(interpolationDataPoints);

        return new ShooterAngleAdjustmentSystem(controller, angleSupplier, pidController, interpolation);
    }

    public double angle() {
        return angleSupplier.getAsDouble();
    }

    public double determineAngle(double distance) {
        return interpolation.applyAsDouble(distance);
    }

    public void resetPidController() {
        pidController.reset();
    }

    public void rotateToDeterminedAngle(double distance) {
        rotateTo(determineAngle(distance));
    }

    public void rotateTo(double target) {
        rotate(pidController.calculate(angle(), target));
    }

    public boolean hasReachedAngle(double target) {
        return ExtendedMath.equals(angle(), target, DEFAULT_MARGIN);
    }

    @Override
    public void rotate(double speed) {
        controller.set(speed);
    }

    @Override
    public void stop() {
        controller.stopMotor();
    }
}
