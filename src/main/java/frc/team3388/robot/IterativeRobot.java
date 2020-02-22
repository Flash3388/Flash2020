package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.subsystems.*;

public class IterativeRobot implements IterativeRobotInterface {
    private final Joystick systemController;
    private final Joystick right;
    private final Joystick left;

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
        right.getButton(0).whileHeld(shooterSystem.keepAtAction(() -> 5000));
        right.getButton(2).whileHeld(ActionFactory.fullFeedAction(intakeSystem, hopperSystem, feederSystem));
        right.getButton(1).whileHeld(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem));
        left.getButton(0).whileHeld(ActionFactory.fullLowShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem));
        left.getButton(1).whileHeld(ActionFactory.rotateTurretByVision(turretSystem, visionSystem));
//        systemController.getButton(0).whileHeld(ActionFactory.fullHighShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, () -> 5000));
//        systemController.getButton(1).whileHeld(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem));
//        systemController.getButton(2).whileHeld(ActionFactory.rotateTurretByVision(turretSystem, visionSystem));
        turretSystem.setDefaultAction(ActionFactory.manualTurretAction(turretSystem, right));
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
