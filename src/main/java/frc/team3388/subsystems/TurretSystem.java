package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.jmath.ExtendedMath;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.objects.NetworkDoubleProperty;
import frc.team3388.objects.SrxEncoder;

public class TurretSystem extends PreciseRotatableSubsystem {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 110;
    private static final int LARGE_GEAR_TOOTH_COUNT = 110;
    private static final int LITTLE_GEAR_TOOTH_COUNT = 27;
    private static final double PID_LIMIT = 0.3;
    private static final double DEFAULT_DELTA = 0.5;

    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController pidController, SrxEncoder encoder, double maxAngle) {
        super(new FrcSpeedController(controller), encoder, pidController, PID_LIMIT, DEFAULT_DELTA);
        this.maxAngle = maxAngle;
    }

    public static TurretSystem forRobot() {
        NetworkTable pid = NetworkTableInstance.getDefault().getTable("pid");
        final NetworkDoubleProperty kP = new NetworkDoubleProperty(pid.getEntry("kP"));
        final NetworkDoubleProperty kI = new NetworkDoubleProperty(pid.getEntry("kI"));
        final NetworkDoubleProperty kD = new NetworkDoubleProperty(pid.getEntry("kD"));
        final NetworkDoubleProperty kF = new NetworkDoubleProperty(pid.getEntry("kF"));

        PidController pidController = new PidController(kP, kI, kD, kF);
        WPI_TalonSRX talon = new WPI_TalonSRX(CONTROLLER_PORT);
        talon.setInverted(true);
        SrxEncoder encoder = new SrxEncoder(talon, LITTLE_GEAR_TOOTH_COUNT / (double) LARGE_GEAR_TOOTH_COUNT * 360);
        encoder.reset();

        return new TurretSystem(talon, pidController, encoder, DEFAULT_MAX_ANGLE);
    }

    @Override
    public void rotate(double speed) {
        speed = !canRotate() && Math.signum(speed) == Math.signum(currentValue()) ? 0 : speed;
        super.rotate(speed);
    }

    public boolean canRotate() {
        return ExtendedMath.constrained(currentValue(), -maxAngle, maxAngle);
    }
}