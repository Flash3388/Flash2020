package frc.team3388.robot;

import com.beans.properties.SimpleProperty;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import com.flash3388.flashlib.robot.scheduling.triggers.TriggerState;
import com.flash3388.flashlib.robot.scheduling.triggers.TriggerStateListener;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.actions.AutonomousActionFactory;
import frc.team3388.actions.TurretPosition;
import frc.team3388.subsystems.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IterativeRobot implements IterativeRobotInterface {
    private final Action autoAction;

    public IterativeRobot(Robot robot) {
        SimpleProperty<TurretPosition> initialPositionProperty = new SimpleProperty<>();
        DriveSystem driveSystem = DriveSystem.forRobot();
        IntakeSystem intakeSystem = IntakeSystem.forRobot();
        HopperSystem hopperSystem = HopperSystem.forRobot();
        FeederSystem feederSystem = FeederSystem.forRobot();
        ShooterSystem shooterSystem = ShooterSystem.forRobot();
        TurretSystem turretSystem = TurretSystem.forRobot();
        ClimbSystem climbSystem = ClimbSystem.forRobot();
        VisionSystem visionSystem = VisionSystem.forRobot();

        autoAction = AutonomousActionFactory.genericAuto(driveSystem, intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, robot.getClock());

        setupTests(driveSystem, intakeSystem, hopperSystem, feederSystem, shooterSystem, turretSystem, climbSystem);

        Joystick systemController = new Joystick(0);
        Joystick right = new Joystick(1);
        Joystick left = new Joystick(2);

        driveSystem.setDefaultAction(ActionFactory.manualDriveAction(driveSystem, right, left));
        turretSystem.setDefaultAction(ActionFactory.manualTurretAction(turretSystem, systemController));

        left.getButton(0).whileHeld(ActionFactory.fullLowShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem));
        left.getButton(1).whenPressed(Actions.instantAction(robot.getScheduler()::stopAllActions));
        right.getButton(0).whileHeld(ActionFactory.initiationLineShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem));
        right.getButton(1).whileHeld(new RotateAction(hopperSystem, () -> -0.8));

        systemController.getButton(0).whileHeld(ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, initialPositionProperty));
        systemController.getButton(1).whileHeld(ActionFactory.fullEatAction(intakeSystem, hopperSystem, robot.getClock()));
        systemController.getButton(2).whenPressed(ActionFactory.foldIntakeAction(intakeSystem));
        systemController.getButton(5).whenPressed(climbSystem.rise());
        systemController.getButton(3).whileHeld(climbSystem.rotateAction());

        setupTurretControl(initialPositionProperty, systemController);
        right.getButton(0).whileHeld(ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, initialPositionProperty));
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

    private static void setupTests(Testable... testables) {
        List<Action> testActions = Arrays.stream(testables)
                .map(Testable::tests)
                .collect(Collectors.toList());

        NetworkTableEntry shouldTest = NetworkTableInstance.getDefault().getTable("test").getEntry("shouldTest");
        shouldTest.setBoolean(false);
        shouldTest.addListener(entryNotification -> {
            if(entryNotification.value.getBoolean())
                testActions.forEach(Action::start);
            entryNotification.getEntry().setBoolean(false);
        }, EntryListenerFlags.kUpdate);
    }

    private static void setupTurretControl(SimpleProperty<TurretPosition> initialPositionProperty, Joystick controller) {
        Map<Integer, TurretPosition> povToTurretPositionMap = new HashMap<>();
        povToTurretPositionMap.put(0, TurretPosition.FRONT);
        povToTurretPositionMap.put(1, TurretPosition.RIGHT);
        povToTurretPositionMap.put(3, TurretPosition.LEFT);
        Trigger povTrigger = new Trigger();
        povTrigger.whenActive(Actions.instantAction(() -> initialPositionProperty.set(povToTurretPositionMap.get(controller.getPov(0).getAsInt()))));
        povTrigger.setState(TriggerState.ACTIVE);
    }
}
