package frc.team3388.actions;

import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.DriveSystem;
import frc.team3388.subsystems.*;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ActionFactory {
    public static Action manualDriveAction(DriveSystem driveSystem, Joystick right, Joystick left) {
        return new TankDriveAction(driveSystem, right.getAxis(JoystickAxis.Y), left.getAxis(JoystickAxis.Y));
    }

    public static Action rotateTurretByVision(TurretSystem turretSystem, VisionSystem visionSystem) {
        double initialTurretAngle = turretSystem.currentValue();

        return Actions.sequential(
                Actions.wait(Time.milliseconds(100)),
                turretSystem.keepAtAction(() -> initialTurretAngle + visionSystem.alignmentError())
        ).requires(turretSystem, visionSystem);
    }

    public static Action fullShootAction(ShooterSystem shooterSystem, ShooterFeederSystem feederSystem, HopperSystem hopperSystem, DoubleSupplier rpm) {
        return Actions.sequential(
                shooterSystem.roughRotateToAction(rpm.getAsDouble()),
                Actions.parallel(
                        shooterSystem.keepAtAction(rpm),
                        fullFeedAction(feederSystem, hopperSystem)
                )
        ).requires(shooterSystem, feederSystem, hopperSystem);
    }

    public static Action highInterpolateShootAction(ShooterSystem shooterSystem, double distance) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::hideLid),
                shooterSystem.keepAtAction(() -> shooterSystem.interpolateRpm(distance))
        );
    }

    public static Action lowShootAction(ShooterSystem shooterSystem) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::closeLid),
                new RotateAction(shooterSystem, () -> 0.5)
        );
    }

    public static Action fullFeedAction(ShooterFeederSystem feederSystem, HopperSystem hopperSystem) {
        return Actions.parallel(
                feederSystem.rotateAction(),
                hopperSystem.rotateAction()
        ).requires(hopperSystem, feederSystem);
    }

    public static Action fullIntakeAction(IntakeSystem intakeSystem, HopperSystem hopperSystem) {
        return Actions.parallel(
                intakeSystem.rotateAction(),
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
