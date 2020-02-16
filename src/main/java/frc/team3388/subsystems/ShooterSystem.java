package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedControllerGroup;
import com.jmath.interpolation.Interpolation;
import com.jmath.interpolation.LagrangePolynomial;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.Piston;
import frc.team3388.objects.SrxEncoder;

import java.util.HashMap;
import java.util.Map;

public class ShooterSystem extends PreciseRotatableSubsystem {
    private static final int FIRST_CONTROLLER_PORT = 11;
    private static final int SECOND_CONTROLLER_PORT = 10;
    private static final int PISTON_FORWARD_PORT = 1;
    private static final int PISTON_REVERSE_PORT = 0;

    private final Piston lid;
    private final Interpolation interpolation;

    public ShooterSystem(SpeedController firstSpeedController, SpeedController secondSpeedController, SrxEncoder encoderSrx, PidController pidController, Piston lid, Interpolation interpolation) {
        super(new SpeedControllerGroup(new FrcSpeedController(firstSpeedController), new FrcSpeedController(secondSpeedController)), () -> encoderSrx.getVelocityPerSecond() * 60, pidController, 1, 50);
        this.lid = lid;
        this.interpolation = interpolation;
    }

    public static ShooterSystem forRobot() {
        final double kP = 0.002;
        final double kI = 0;
        final double kD = 0.001;

        PidController pidController = new PidController(kP, kI, kD, 0);
        WPI_TalonSRX firstSpeedController = new WPI_TalonSRX(FIRST_CONTROLLER_PORT); firstSpeedController.setInverted(true);
        WPI_VictorSPX secondSpeedController = new WPI_VictorSPX(SECOND_CONTROLLER_PORT); secondSpeedController.setInverted(true);
        SrxEncoder encoderSrx = new SrxEncoder(firstSpeedController, 1);
//        Piston lid = new Piston(PISTON_FORWARD_PORT, PISTON_REVERSE_PORT);
        Interpolation interpolation = LagrangePolynomial.fromMap(dataPoints());

        secondSpeedController.follow(firstSpeedController);

        return new ShooterSystem(firstSpeedController, secondSpeedController, encoderSrx, pidController, null, interpolation);
    }

    public void hideLid() {
        lid.close();
    }

    public void closeLid() {
        lid.open();
    }

    public double interpolateRpm(double distance) {
        return interpolation.applyAsDouble(distance);
    }

    @Override
    public void rotateTo(double target) {
        super.rotateTo(target + 250);
    }

    private static Map<Double, Double> dataPoints() {
        return new HashMap<>();
    }
}
