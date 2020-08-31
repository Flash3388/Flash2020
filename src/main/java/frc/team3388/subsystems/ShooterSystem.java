package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.jmath.ExtendedMath;
import com.jmath.util.ArrayUnboxer;
import frc.team3388.objects.NetworkDoubleProperty;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class ShooterSystem extends PreciseRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 10;
    private static final double LOW_SHOOT_PERCENTAGE = 0.2;

    private final UnivariateFunction interpolation;
    private final WPI_TalonFX controller;
    private boolean hasReset;
    private final NetworkDoubleProperty rpm;

    public ShooterSystem(WPI_TalonFX controller, DoubleSupplier rpmSupplier, PidController pidController, UnivariateFunction interpolation) {
        super(new FrcSpeedController(controller), rpmSupplier, pidController, 100);
        this.interpolation = interpolation;
        this.controller = controller;
        rpm = new NetworkDoubleProperty("tests", "rpm", 0);
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
        PolynomialFunctionLagrangeForm interpolation = new PolynomialFunctionLagrangeForm(ArrayUnboxer.unbox(xyValues.keySet().toArray(new Double[0])), ArrayUnboxer.unbox(xyValues.values().toArray(new Double[0])));

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
        System.out.println("distance " + distance + " rpm " + interpolation.value(distance));
        return interpolation.value(distance);
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
        super.rotateTo(() -> ExtendedMath.constrain(target.getAsDouble(), 3750, 5700) + 1300);
    }

    @Override
    public void rotate(double speed) {
        rpm.setAsDouble(currentValue());
        super.rotate(speed > 0 ? speed : 0);
    }

    private static Map<Double, Double> dataPoints() {
        HashMap<Double, Double> interpolationPoints = new HashMap<>();
        interpolationPoints.put(280.0, 5000.0);
        interpolationPoints.put(300.0, 4100.0);
        interpolationPoints.put(320.0, 4000.0);
        interpolationPoints.put(340.0, 3835.0);
        interpolationPoints.put(360.0, 3800.0);
        interpolationPoints.put(380.0, 3970.0);
        interpolationPoints.put(400.0, 4085.0);
        interpolationPoints.put(420.0, 4110.0);
        interpolationPoints.put(440.0, 4200.0);
        interpolationPoints.put(460.0, 4280.0);
        interpolationPoints.put(480.0, 4350.0);
        interpolationPoints.put(500.0, 4410.0);
        interpolationPoints.put(520.0, 4490.0);
        interpolationPoints.put(540.0, 4550.0);
        interpolationPoints.put(560.0, 4630.0);
        interpolationPoints.put(580.0, 4700.0);
        interpolationPoints.put(600.0, 4800.0);
        interpolationPoints.put(620.0, 4950.0);
        interpolationPoints.put(640.0, 5100.0);
        interpolationPoints.put(660.0, 5350.0);
        interpolationPoints.put(680.0, 5450.0);
        interpolationPoints.put(700.0, 5550.0);
        interpolationPoints.put(720.0, 5700.0);
        interpolationPoints.put(740.0, 5700.0);

        return interpolationPoints;
    }
}
