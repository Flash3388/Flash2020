package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.jmath.ExtendedMath;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.actions.ActionFactory;
import frc.team3388.objects.SrxEncoder;

import java.util.function.DoubleSupplier;

public class TurretSystem extends PreciseRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 110;
    private static final int LARGE_GEAR_TOOTH_COUNT = 110;
    private static final int LITTLE_GEAR_TOOTH_COUNT = 27;
    private static final double LIMIT = 0.4;
    private static final double DEFAULT_DELTA = 1;

    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController pidController, SrxEncoder encoder, double maxAngle) {
        super(new FrcSpeedController(controller), encoder, pidController, DEFAULT_DELTA);
        this.maxAngle = maxAngle;
    }

    public static TurretSystem forRobot() {
        final double kP = 0.08;
        final double kD = 0.19;

        PidController pidController = new PidController(kP, 0, kD, 0);
        WPI_TalonSRX talon = new WPI_TalonSRX(CONTROLLER_PORT);
        talon.setInverted(true);
        SrxEncoder encoder = new SrxEncoder(talon, LITTLE_GEAR_TOOTH_COUNT / (double) LARGE_GEAR_TOOTH_COUNT * 360);
        encoder.reset();

        return new TurretSystem(talon, pidController, encoder, DEFAULT_MAX_ANGLE);
    }

    @Override
    public void rotate(double speed) {
        speed = ExtendedMath.constrain(speed, -LIMIT, LIMIT);
        speed = !canRotate() && Math.signum(speed) == Math.signum(currentValue()) ? 0 : speed;
        super.rotate(speed);
    }

    public boolean canRotate() {
        return ExtendedMath.constrained(currentValue(), -maxAngle, maxAngle);
    }
}