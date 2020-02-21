package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.jmath.ExtendedMath;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.actions.ActionFactory;
import frc.team3388.objects.NetworkDoubleSupplier;
import frc.team3388.objects.SrxEncoder;

import java.util.function.DoubleSupplier;

public class TurretSystem extends PreciseRotatableSubsystem {
    private static final int CONTROLLER_PORT = 3;
    private static final double DEFAULT_MAX_ANGLE = 110;
    private static final int LARGE_GEAR_TOOTH_COUNT = 110;
    private static final int LITTLE_GEAR_TOOTH_COUNT = 27;
    private static final double VISION_PID_LIMIT = 0.3;
    private static final double ENCODER_PID_LIMIT = 0.4;
    private static final double DEFAULT_DELTA = 0.5;

    private final PreciseRotatableSubsystem visionBased;
    private final PreciseRotatableSubsystem encoderBased;
    private final double maxAngle;

    public TurretSystem(SpeedController controller, PidController visionPid, PidController encoderPid, DoubleSupplier visionSupplier, DoubleSupplier encoderAngleSupplier, double maxAngle) {
        super(new FrcSpeedController(controller), encoderAngleSupplier, encoderPid);
        visionBased = new PreciseRotatableSubsystem(new FrcSpeedController(controller), visionSupplier, visionPid);
        encoderBased = new PreciseRotatableSubsystem(new FrcSpeedController(controller), encoderAngleSupplier, encoderPid);
        this.maxAngle = maxAngle;
    }

    public static TurretSystem forRobot() {
        final double visionKP = 0.06;
        final double encoderKP = 0.1;
        final double encoderKD = 0.1;

        WPI_TalonSRX talon = new WPI_TalonSRX(CONTROLLER_PORT);

        PidController visionPid = new PidController(visionKP, 0, 0, 0);
        visionPid.setOutputLimit(VISION_PID_LIMIT);
        DoubleSupplier visionAngleSupplier = new NetworkDoubleSupplier("vision", "angle_degrees", 0);
        PidController encoderPid = new PidController(encoderKP, 0, encoderKD, 0);
        encoderPid.setOutputLimit(ENCODER_PID_LIMIT);
        SrxEncoder encoderAngleSupplier = new SrxEncoder(talon, LITTLE_GEAR_TOOTH_COUNT/(double)LARGE_GEAR_TOOTH_COUNT * 360);
        encoderAngleSupplier.reset();

        return new TurretSystem(talon, visionPid, encoderPid, visionAngleSupplier, encoderAngleSupplier, DEFAULT_MAX_ANGLE);
    }

    @Override
    public Action roughRotateToAction(double target) {
        return ActionFactory.onCondition(keepAtEncoder(() -> target), () -> encoderBased.hasReachedTarget(target));
    }

    @Override
    public Action keepAtAction(DoubleSupplier target) {
        return new GenericActionBuilder()
                .onInitialize(visionBased::resetPidController)
                .onExecute(() -> visionBased.rotateTo(target))
                .onEnd(visionBased::stop)
                .runOnEndWhenInterrupted()
                .requires(this)
                .build();
    }

    @Override
    public void rotate(double speed) {
        speed = !canRotate() && Math.signum(speed) != Math.signum(currentValue()) ? 0 : speed;
        super.rotate(speed);
    }

    public boolean canRotate() {
        return ExtendedMath.constrained(currentValue(), -maxAngle, maxAngle);
    }

    private Action keepAtEncoder(DoubleSupplier target) {
        return new GenericActionBuilder()
                .onInitialize(encoderBased::resetPidController)
                .onExecute(() -> encoderBased.rotateTo(target))
                .onEnd(encoderBased::stop)
                .runOnEndWhenInterrupted()
                .requires(this)
                .build();
    }
}
