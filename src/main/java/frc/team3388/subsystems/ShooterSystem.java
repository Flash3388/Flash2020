package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.jmath.interpolation.Interpolation;
import com.jmath.interpolation.LagrangePolynomial;
import edu.wpi.first.wpilibj.controller.PIDController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class ShooterSystem extends PreciseRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 10;
    private static final double LOW_SHOOT_PERCENTAGE = 0.25;

    private final Interpolation interpolation;
    private final WPI_TalonFX controller;

    public ShooterSystem(WPI_TalonFX controller, DoubleSupplier rpmSupplier, PIDController pidController, Interpolation interpolation) {
        super(new FrcSpeedController(controller), rpmSupplier, pidController, 10);
        this.interpolation = interpolation;
        this.controller = controller;
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.001 / 3.0;
        final double kI = 0;
        final double kD = 0.0005 / 3.0;

        PIDController pidController = new PIDController(kP, kI, kD);
        WPI_TalonFX controller = new WPI_TalonFX(CONTROLLER_PORT);
        Interpolation interpolation = LagrangePolynomial.fromMap(dataPoints());

        return new ShooterSystem(controller,() -> controller.getSelectedSensorVelocity() / 4096.0 * 600, pidController, interpolation);
    }

    public Action lowShootAction() {
        return new GenericActionBuilder()
                .onExecute(() -> rotate(LOW_SHOOT_PERCENTAGE))
                .onEnd(this::stop)
                .runOnEndWhenInterrupted()
                .build().requires(this);
    }

    public double interpolateRpm(double distance) {
        return interpolation.applyAsDouble(distance);
    }

    public void resetEncoder() {
        controller.setSelectedSensorPosition(0);
    }

    @Override
    public void rotate(double speed) {
        super.rotate(speed > 0 ? speed : 0);
    }

    private static Map<Double, Double> dataPoints() {
        HashMap<Double, Double> interpolationPoints = new HashMap<>();

        return interpolationPoints;
    }
}
