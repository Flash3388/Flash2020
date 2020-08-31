package frc.team3388.actions;

import com.beans.DoubleProperty;
import com.beans.properties.SimpleDoubleProperty;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.hid.JoystickAxis;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.flash3388.flashlib.robot.systems.drive.actions.TankDriveAction;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ActionFactory {
    public static Action manualDriveAction(DriveSystem driveSystem, Joystick right, Joystick left) {
        return new TankDriveAction(driveSystem, () -> right.getAxis(JoystickAxis.Y).getAsDouble(), () -> left.getAxis(JoystickAxis.Y).getAsDouble());
    }

    public static Action manualTurretAction(TurretSystem turretSystem, Joystick joystick) {
        return new RotateAction(turretSystem, () -> joystick.getAxis(JoystickAxis.Z).getAsDouble() * 0.25).requires(turretSystem);
    }

    public static Action visionShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, TurretSystem turretSystem, ShooterSystem shooterSystem, VisionSystem visionSystem, Supplier<TurretPosition> initialPositionSupplier) {
        DoubleProperty distance = new SimpleDoubleProperty();

        return Actions.sequential(
                enableVisionProcessingMode(visionSystem),
                Actions.wait(Time.milliseconds(100)),
                        Actions.sequential(
                                turretSystem.roughRotateToAction(() -> visionSystem.hasFoundTarget() ? turretSystem.currentValue() : initialPositionSupplier.get().value()),
                                rotateTurretByVision(turretSystem, visionSystem).setTimeout(Time.seconds(1)),
                                Actions.wait(Time.milliseconds(200)),
                                Actions.instantAction(() -> System.out.println(visionSystem.distance() + " " + shooterSystem.interpolateRpm(distance.getAsDouble()))),
                                Actions.instantAction(() -> distance.setAsDouble(visionSystem.distance())),
                                parallel(
                                        rotateTurretByVision(turretSystem, visionSystem),
                                        interpolateShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, distance)
                                )
                        )
        ).requires(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem);
    }

    public static Action rotateTurretByVision(TurretSystem turretSystem, VisionSystem visionSystem) {
        return Actions.sequential(
                enableVisionProcessingMode(visionSystem),
                new GenericActionBuilder()
                        .onExecute(() -> turretSystem.rotateTo(() -> turretSystem.currentValue() + visionSystem.alignmentError()))
                        .onEnd(turretSystem::stop)
                        .runOnEndWhenInterrupted()
                        .build().requires(turretSystem, visionSystem)
        ).requires(turretSystem, visionSystem);
    }

    public static Action initiationLineShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, TurretSystem turretSystem, ShooterSystem shooterSystem) {
        DoubleProperty initialAngle = new SimpleDoubleProperty();

        return parallel(
                new GenericActionBuilder()
                        .onInitialize(() -> initialAngle.set(turretSystem.currentValue()))
                        .onExecute(() -> turretSystem.rotateTo(initialAngle))
                        .onEnd(turretSystem::stop)
                        .runOnEndWhenInterrupted()
                        .build().requires(turretSystem),
                interpolateShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, () -> 300)
        );
    }

    public static Action interpolateShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem, DoubleSupplier distance) {
        return Actions.sequential(
                Actions.instantAction(hopperSystem::reset),
                fullHighShootAction(intakeSystem, hopperSystem, feederSystem, shooterSystem, () -> shooterSystem.interpolateRpm(distance.getAsDouble()))
        );
    }

    public static Action fullHighShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem, DoubleSupplier rpm) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::resetEncoder),
                shooterSystem.roughRotateToAction(rpm),
                parallel(
                        shooterSystem.keepAtAction(rpm),
                        fullFeedAction(intakeSystem, hopperSystem, feederSystem)
                )
        ).requires(intakeSystem, hopperSystem, feederSystem, shooterSystem);
    }

    public static Action fullLowShootAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, ShooterSystem shooterSystem) {
        return parallel(
                shooterSystem.lowShootAction(),
                fullFeedAction(intakeSystem, hopperSystem, feederSystem)
        );
    }

    public static Action fullFeedAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem) {
        return Actions.sequential(
                Actions.wait(Time.milliseconds(50)),
                parallel(
                        fullIntakeAction(intakeSystem, hopperSystem),
                        feederSystem.rotateAction()
                )
        ).requires(intakeSystem, hopperSystem, feederSystem);
    }

    public static Action fullEatAction(IntakeSystem intakeSystem, HopperSystem hopperSystem, Clock clock) {
        return parallel(
                engageIntakeAction(intakeSystem),
                hopperSystem.eatAction(clock)
        );
    }

    public static Action fullIntakeAction(IntakeSystem intakeSystem, HopperSystem hopperSystem) {
        return parallel(
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

    public static Action until(Action action, BooleanSupplier condition) {
        Collection<Subsystem> requirements = action.getRequirements();
        action.resetRequirements();

        return new GenericActionBuilder()
                .onInitialize(action::start)
                .onEnd(action::cancel)
                .isFinished(condition)
                .runOnEndWhenInterrupted()
                .build()
                .requires(requirements);
    }

    public static Action switchCamAction(VisionSystem visionSystem) {
        return Actions.instantAction(visionSystem::switchCam);
    }

    public static Action setTurretCamAction(VisionSystem visionSystem) {
        return Actions.instantAction(visionSystem::switchToTurretCam).requires(visionSystem);
    }

    public static Action setFrontCamAction(VisionSystem visionSystem) {
        return Actions.instantAction(visionSystem::switchToFrontCam).requires(visionSystem);
    }

    public static Action parallel(Action... actions) {
        List<Subsystem> requirements = new ArrayList<>();
        for(Action action: actions)
            requirements.addAll(action.getRequirements());

        for(Action action: actions)
            action.resetRequirements();

        return new GenericActionBuilder()
                .onInitialize(() -> {
                    for(Action action: actions)
                        action.start();
                })
                .onEnd(() -> {
                    for(Action action: actions)
                        action.cancel();
                })
                .runOnEndWhenInterrupted()
                .build()
                .requires(requirements);
    }

    private static Action enableVisionProcessingMode(VisionSystem visionSystem) {
        return Actions.sequential(
                setTurretCamAction(visionSystem),
                Actions.instantAction(visionSystem::setProcessingMode),
                Actions.wait(Time.milliseconds(20))
        ).requires(visionSystem);
    }
}
