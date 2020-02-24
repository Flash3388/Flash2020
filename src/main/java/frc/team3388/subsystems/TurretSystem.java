package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.jmath.ExtendedMath;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.SrxEncoder;

public class TurretSystem extends PreciseRotatableSubsystem {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 110;
    private static final int LARGE_GEAR_TOOTH_COUNT = 110;
    private static final int LITTLE_GEAR_TOOTH_COUNT = 27;
    private static final double PID_LIMIT = 0.3;
    private static final double DEFAULT_DELTA = 1;

    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController pidController, SrxEncoder encoder, double maxAngle) {
        super(new FrcSpeedController(controller), encoder, pidController, PID_LIMIT, DEFAULT_DELTA);
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

//    @Override
//    public void rotateTo(DoubleSupplier target) {
//        super.rotateTo(ExtendedMath.equals(target.getAsDouble(), currentValue(), DEFAULT_DELTA) ? () -> 0 :  target);
//    }

    @Override
    public void rotate(double speed) {
        speed = !canRotate() && Math.signum(speed) == Math.signum(currentValue()) ? 0 : speed;
        super.rotate(speed);
    }

    public boolean canRotate() {
        return ExtendedMath.constrained(currentValue(), -maxAngle, maxAngle);
    }
}