package frc.team3388.subsystems;

import calculus.trajectories.TankTrajectory;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedControllerGroup;
import com.flash3388.flashlib.robot.motion.Direction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.time.Clock;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.team3388.objects.SrxEncoder;
import tracer.actions.TankFollowerAction;
import tracer.controllers.factories.TankTrajectoryControllerFactory;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.following.TankFollower;
import tracer.motion.MotionState;

public class DriveSystem extends TankDriveSystem implements TankFollower {
    private static final int FRONT_RIGHT_CONTROLLER_PORT = 1;
    private static final int REAR_RIGHT_CONTROLLER_PORT = 2;
    private static final int FRONT_LEFT_CONTROLLER_PORT = 5;
    private static final int REAR_LEFT_CONTROLLER_PORT = 7;
    private static final MotionState DEFAULT_MAX_PARAMETERS = MotionState.frcKitOfPartsParameters(1.5);

    private final TankTrajectoryControllerFactory controllerFactory;
    private final Gyro gyro;
    private final SrxEncoder rightEncoder;
    private final SrxEncoder leftEncoder;

    public DriveSystem(SpeedController frontRight, SpeedController rearRight, SpeedController frontLeft, SpeedController rearLeft, TankTrajectoryControllerFactory controllerFactory, Gyro gyro, SrxEncoder rightEncoder, SrxEncoder leftEncoder) {
        super(new SpeedControllerGroup(frontRight, rearRight), new SpeedControllerGroup(frontLeft, rearLeft));
        this.controllerFactory = controllerFactory;
        this.rightEncoder = rightEncoder;
        this.leftEncoder = leftEncoder;
        this.gyro = gyro;
    }

    public static DriveSystem forRobot() {
        final double CM_TO_M_RATIO = 0.01;

        WPI_TalonSRX frontRight = new WPI_TalonSRX(FRONT_RIGHT_CONTROLLER_PORT);
        WPI_TalonSRX rearRight = new WPI_TalonSRX(REAR_RIGHT_CONTROLLER_PORT);
        WPI_TalonSRX frontLeft = new WPI_TalonSRX(FRONT_LEFT_CONTROLLER_PORT); frontLeft.setInverted(true);
        WPI_TalonSRX rearLeft = new WPI_TalonSRX(REAR_LEFT_CONTROLLER_PORT); rearLeft.setInverted(true);
        TankTrajectoryControllerFactory controllerFactory = createControllerFactory();

        return new DriveSystem(
                new FrcSpeedController(frontRight), new FrcSpeedController(rearRight),
                new FrcSpeedController(frontLeft), new FrcSpeedController(rearLeft),
                controllerFactory, null, new SrxEncoder(frontRight, CM_TO_M_RATIO), new SrxEncoder(frontLeft, CM_TO_M_RATIO));
    }

    public Action autonomousForwardDrive(TankTrajectory trajectory, Clock clock) {
        return new TankFollowerAction(this, controllerFactory.create(trajectory, DEFAULT_MAX_PARAMETERS, RobotController.getBatteryVoltage(), Direction.FORWARD), clock);
    }

    public Action autonomousBackwardDrive(TankTrajectory trajectory, Clock clock) {
        return new TankFollowerAction(this, controllerFactory.create(trajectory, DEFAULT_MAX_PARAMETERS.mul(-1), RobotController.getBatteryVoltage(), Direction.BACKWARD), clock);
    }

    public Action autonomousDrive(TankTrajectory trajectory, MotionState max, double maxVoltage, Direction direction, Clock clock) {
        return new TankFollowerAction(this, controllerFactory.create(trajectory, max, maxVoltage, direction), clock);
    }

    public void calibrateGyro() {
        gyro.calibrate();
    }

    @Override
    public double passedDistanceLeftM() {
        return leftEncoder.getAsDouble();
    }

    @Override
    public double passedDistanceRightM() {
        return rightEncoder.getAsDouble();
    }

    @Override
    public double angle() {
        return gyro.getAngle();
    }

    private static  TankTrajectoryControllerFactory createControllerFactory() {
        final double kP = 0;
        final double kI = 0;
        final double kD = 0;

        final double kV = 2;
        final double kA = 2;

        final double gP = 0.2;

        PidControllerParameters pidControllerParameters = new PidControllerParameters(kP, kI, kD);
        MotionControllerParameters motionControllerParameters = new MotionControllerParameters(kV, kA, 0);

        return new TankTrajectoryControllerFactory(pidControllerParameters, motionControllerParameters, gP);
    }
}
