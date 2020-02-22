package frc.team3388.actions;

import com.beans.DoubleProperty;
import com.beans.properties.SimpleDoubleProperty;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.flash3388.flashlib.robot.scheduling.actions.SequentialActionGroup;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.*;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ActionFactory {
    public static Action manualDriveAction(DriveSystem driveSystem, Joystick right, Joystick left) {
        return new TankDriveAction(driveSystem, right.getAxis(JoystickAxis.Y), left.getAxis(JoystickAxis.Y));
    }

    public static Action manualTurretAction(TurretSystem turretSystem, Joystick joystick) {
        return new RotateAction(turretSystem, () -> -joystick.getAxis(JoystickAxis.Z).getAsDouble() * 0.4);
    }

    public static Action visionShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem, TurretSystem turretSystem, VisionSystem visionSystem) {
        return Actions.sequential(
                Actions.wait(Time.milliseconds(100)),
                Actions.parallel(
                        rotateTurretByVision(turretSystem, visionSystem),
                        interpolateShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, visionSystem.distance())
                )
        );
    }

    public static Action rotateTurretByVision(TurretSystem turretSystem, VisionSystem visionSystem) {
        DoubleProperty initialValue = new SimpleDoubleProperty();

        return new GenericActionBuilder()
                .onInitialize(() -> {visionSystem.setProcessingMode(); initialValue.set(turretSystem.currentValue());})
                .onExecute(() -> turretSystem.rotateTo(() -> initialValue.get() + visionSystem.alignmentError() * 2))
                .onEnd(() ->  {visionSystem.setNormalMode(); turretSystem.stop();})
                .runOnEndWhenInterrupted()
                .build()
                .requires(turretSystem, visionSystem);
    }

    public static Action interpolateShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem, double distance) {
        return fullHighShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, () -> shooterSystem.interpolateRpm(distance));
    }

    public static Action fullHighShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem, DoubleSupplier rpm) {
        return new SequentialActionGroup() {
            @Override
            public String toString() {
                return "the one";
            }
        }.add(
                shooterSystem.roughRotateToAction(rpm.getAsDouble()),
                Actions.parallel(
                        shooterSystem.keepAtAction(rpm),
                        fullFeedAction(intakeSystem, hopperSystem, feederSystem)
                )
        );
    }

    public static Action fullLowShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem) {
        return Actions.parallel(
                shooterSystem.lowShootAction(),
                fullFeedAction(intakeSystem, hopperSystem, feederSystem)
        );
    }

    public static Action fullFeedAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem) {
        return Actions.parallel(
                fullIntakeAction(intakeSystem, hopperSystem),
                feederSystem.rotateAction()
        ).requires(hopperSystem, feederSystem);
    }

    public static Action fullIntakeAction(IntakeSystem intakeSystem, HopperSystem hopperSystem) {
        return Actions.parallel(
                engageIntakeAction(intakeSystem),
                hopperSystem.rotateAction()
        );
    }

    public static Action engageIntakeAction(IntakeSystem intakeSystem) {
        return Actions.sequential(
                unfoldIntakeAction(intakeSystem),
                intakeSystem.rotateAction()
        ).requires(intakeSystem);
    }

    public static Action unfoldIntakeAction(IntakeSystem intakeSystem) {
        return Actions.instantAction(intakeSystem::unfold).requires(intakeSystem);
    }

    public static Action foldIntakeAction(IntakeSystem intakeSystem) {
        return Actions.instantAction(intakeSystem::fold).requires(intakeSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new GenericActionBuilder()
                .onInitialize(action::start)
                .onEnd(action::cancel)
                .isFinished(condition)
                .runOnEndWhenInterrupted()
                .build();
    }
}
