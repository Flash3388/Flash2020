package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.actions.AutonomousActionFactory;
import frc.team3388.actions.RotateTurretUntilSolidTargetAction;
import frc.team3388.objects.NetworkDoubleProperty;
import frc.team3388.objects.NetworkDoubleSupplier;
import frc.team3388.subsystems.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class IterativeRobot implements IterativeRobotInterface {
    private final Action autoAction;

    public IterativeRobot(Robot robot) {
        DriveSystem driveSystem = DriveSystem.forRobot();
        IntakeSystem intakeSystem = IntakeSystem.forRobot();
        HopperSystem hopperSystem = HopperSystem.forRobot();
        FeederSystem feederSystem = FeederSystem.forRobot();
        ShooterSystem shooterSystem = ShooterSystem.forRobot();
        TurretSystem turretSystem = TurretSystem.forRobot();
        ClimbSystem climbSystem = ClimbSystem.forRobot();
        VisionSystem visionSystem = VisionSystem.forRobot();
        autoAction = AutonomousActionFactory.genericAuto(driveSystem, intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, robot.getClock());

        Queue<Testable> testables = new ArrayDeque<>(Arrays.asList(driveSystem, intakeSystem, hopperSystem, feederSystem, shooterSystem, turretSystem, climbSystem));
        List<Action> testActions = testables.stream()
                .map(Testable::tests)
                .collect(Collectors.toList());

        NetworkTableInstance.getDefault().getTable("test").getEntry("shouldTest").addListener(entryNotification -> {
            if(entryNotification.value.getBoolean())
                testActions.forEach(Action::start);
            entryNotification.getEntry().setBoolean(false);
        }, EntryListenerFlags.kUpdate);

        Joystick systemController = new Joystick(0);
        Joystick right = new Joystick(1);
        Joystick left = new Joystick(2);

        driveSystem.setDefaultAction(ActionFactory.manualDriveAction(driveSystem, right, left));
        turretSystem.setDefaultAction(ActionFactory.manualTurretAction(turretSystem, systemController));

        left.getButton(0).whileHeld(ActionFactory.fullLowShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem));
        left.getButton(1).whenPressed(Actions.instantAction(robot.getScheduler()::stopAllActions));
        right.getButton(0).whileHeld(ActionFactory.initiationLineShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem));
        right.getButton(1).whenPressed(ActionFactory.switchCamAction(visionSystem));
        right.getButton(2).whileHeld(new RotateAction(hopperSystem, () -> -0.8));
        left.getButton(2).whileHeld(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem));
        systemController.getButton(0).whileHeld(ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem));
        systemController.getButton(1).whileHeld(ActionFactory.fullEatAction(intakeSystem, hopperSystem, robot.getClock()));
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
        autoAction.start();
    }

    @Override
    public void autonomousPeriodic() {
        if(!autoAction.isRunning())
            autoAction.start();
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
