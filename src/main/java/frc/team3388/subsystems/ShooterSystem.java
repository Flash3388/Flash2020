package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.jmath.interpolation.Interpolation;
import com.jmath.interpolation.LagrangePolynomial;
import com.jmath.util.ArrayUnboxer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class ShooterSystem extends PreciseRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 10;
    private static final double LOW_SHOOT_PERCENTAGE = 0.2;

    private final Interpolation interpolation;
    private final WPI_TalonFX controller;
    private boolean hasReset;

    public ShooterSystem(WPI_TalonFX controller, DoubleSupplier rpmSupplier, PidController pidController, Interpolation interpolation) {
        super(new FrcSpeedController(controller), rpmSupplier, pidController, 50);
        this.interpolation = interpolation;
        this.controller = controller;
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.0007;
        final double kD = 0.00026;

        PidController pidController = new PidController(kP, 0, kD, 0);
        pidController.setOutputRampRate(0.1);
        pidController.setOutputLimit(0, 1);
        WPI_TalonFX controller = new WPI_TalonFX(CONTROLLER_PORT);

        controller.setInverted(true);
        controller.setSensorPhase(false);
        Map<Double,Double> xyValues = dataPoints();
        LagrangePolynomial interpolation = new LagrangePolynomial(ArrayUnboxer.unbox(xyValues.keySet().toArray(new Double[0])), ArrayUnboxer.unbox(xyValues.values().toArray(new Double[0])));

        return new ShooterSystem(controller,() -> controller.getSelectedSensorVelocity() / 2048.0 * 600, pidController, interpolation);
    }

    public Action lowShootAction() {
        return new GenericActionBuilder()
                .onExecute(() -> rotate(LOW_SHOOT_PERCENTAGE))
                .onEnd(this::stop)
                .runOnEndWhenInterrupted()
                .build().requires(this);
    }

    public double interpolateRpm(double distance) {
        return interpolation.apply(distance);
    }

    public void resetEncoder() {
        controller.setSelectedSensorPosition(0);
        hasReset = true;
    }

    @Override
    public double currentValue() {
        if (hasReset) {
            hasReset = false;
            return 0;
        }
        return super.currentValue();
    }

    @Override
    public void rotateTo(DoubleSupplier target) {
        super.rotateTo(() -> target.getAsDouble()+1300);
    }

    @Override
    public void rotate(double speed) {
        super.rotate(speed > 0 ? speed : 0);
    }

    private static Map<Double, Double> dataPoints() {
        HashMap<Double, Double> interpolationPoints = new HashMap<>();
        interpolationPoints.put(270.0, 4250.0);
        interpolationPoints.put(293.0, 3950.0);
        interpolationPoints.put(323.0, 3870.0);
        interpolationPoints.put(346.0, 3790.0);
        interpolationPoints.put(371.0, 3875.0);
        interpolationPoints.put(401.0, 3975.0);
        interpolationPoints.put(426.0, 4080.0);
        interpolationPoints.put(453.0, 4220.0);
        interpolationPoints.put(478.0, 4360.0);
        interpolationPoints.put(500.0, 4510.0);
        interpolationPoints.put(529.0, 4657.0);
        interpolationPoints.put(554.0, 4800.0);
        interpolationPoints.put(582.0, 4965.0);
        interpolationPoints.put(610.0, 5150.0);
        interpolationPoints.put(635.0, 5320.0);
        interpolationPoints.put(665.0, 5400.0);
        interpolationPoints.put(700.0, 5500.0);
        interpolationPoints.put(743.0, 5850.0);

        return interpolationPoints;
    }
}
