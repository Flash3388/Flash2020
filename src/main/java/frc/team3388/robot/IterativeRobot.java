package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.actions.RotateTurretUntilSolidTargetAction;
import frc.team3388.objects.NetworkDoubleProperty;
import frc.team3388.objects.NetworkDoubleSupplier;
import frc.team3388.subsystems.*;

public class IterativeRobot implements IterativeRobotInterface {
    private final Joystick right;
    private final Joystick left;
    private final Joystick systemController;

    private final Robot robot;
    private final DriveSystem driveSystem;
    private final IntakeSystem intakeSystem;
    private final HopperSystem hopperSystem;
    private final FeederSystem feederSystem;
    private final ShooterSystem shooterSystem;
    private final TurretSystem turretSystem;
    private final ClimbSystem climbSystem;
    private final VisionSystem visionSystem;

    public IterativeRobot(Robot robot) {
        this.robot = robot;
        driveSystem = DriveSystem.forRobot();
        intakeSystem = IntakeSystem.forRobot();
        hopperSystem = HopperSystem.forRobot();
        feederSystem = FeederSystem.forRobot();
        shooterSystem = ShooterSystem.forRobot();
        turretSystem = TurretSystem.forRobot();
        climbSystem = ClimbSystem.forRobot();
        visionSystem = VisionSystem.forRobot();

        systemController = new Joystick(0);
        right = new Joystick(1);
        left = new Joystick(2);

        driveSystem.setDefaultAction(ActionFactory.manualDriveAction(driveSystem, right, left));
//        turretSystem.setDefaultAction(ActionFactory.manualTurretAction(turretSystem, systemController));

        left.getButton(0).whileHeld(ActionFactory.fullLowShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem));
        right.getButton(0).whileHeld(ActionFactory.initiationLineShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem));
        right.getButton(1).whenPressed(ActionFactory.switchCamAction(visionSystem));
        systemController.getButton(0).whileHeld(ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem));
//        systemController.getButton(0).whileHeld(ActionFactory.interpolateShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, 300));
        systemController.getButton(1).whileHeld(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem));
        systemController.getButton(2).whenPressed(ActionFactory.foldIntakeAction(intakeSystem));
        systemController.getButton(5).whenPressed(climbSystem.rise());
        systemController.getButton(3).whileHeld(climbSystem.rotateAction());
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void robotPeriodic() {
    }
}
