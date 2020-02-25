package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedControllerGroup;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.jmath.interpolation.Interpolation;
import com.jmath.interpolation.LagrangePolynomial;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.Piston;
import frc.team3388.objects.SrxEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

public class ShooterSystem extends PreciseRotatableSubsystem {
    private static final int FIRST_CONTROLLER_PORT = 11;
    private static final int SECOND_CONTROLLER_PORT = 10;
    private static final double LOW_SHOOT_PERCENTAGE = 0.2;

    private final Interpolation interpolation;
    private final SrxEncoder srxEncoder;

    public ShooterSystem(SpeedController firstSpeedController, SpeedController secondSpeedControllerSrxEncoder, SrxEncoder encoderSrx, PidController pidController, Piston lid, Interpolation interpolation) {
        super(new SpeedControllerGroup(new FrcSpeedController(firstSpeedController), new FrcSpeedController(secondSpeedControllerSrxEncoder)), () -> encoderSrx.getVelocityPerSecond() * 60, pidController, 1, 1000);
        this.interpolation = interpolation;
        this.srxEncoder = encoderSrx;
        setOutputLimit(0.95);
        setMaxOutputRampRate(0.1);
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.001;
        final double kI = 0;
        final double kD = 0.0005;

        PidController pidController = new PidController(kP, kI, kD, 0);
        WPI_TalonSRX firstSpeedController = new WPI_TalonSRX(FIRST_CONTROLLER_PORT); firstSpeedController.setInverted(true);
        WPI_VictorSPX secondSpeedController = new WPI_VictorSPX(SECOND_CONTROLLER_PORT); secondSpeedController.setInverted(true);
        SrxEncoder encoderSrx = new SrxEncoder(firstSpeedController, -1);
        encoderSrx.reset();
        Interpolation interpolation = LagrangePolynomial.fromMap(dataPoints());


        return new ShooterSystem(firstSpeedController, secondSpeedController, encoderSrx, pidController, null, interpolation);
    }

    public Action lowShootAction() {
        return new GenericActionBuilder()
                .onExecute(() -> rotate(LOW_SHOOT_PERCENTAGE))
                .onEnd(this::stop)
                .runOnEndWhenInterrupted()
                .build().requires(this);
    }

    public double interpolateRpm(double distance) {
        return (distance - 270)/15.0 * 50 + 4250;
    }

    public void resetEncoder() {
        srxEncoder.reset();
    }

    @Override
    public void rotateTo(DoubleSupplier target) {
        super.rotateTo(() -> target.getAsDouble() + 900);
    }

    @Override
    public void rotate(double speed) {
        super.rotate(speed > 0 ? speed : 0);
    }

    private static Map<Double, Double> dataPoints() {
        return new HashMap<>();
    }
}
