package frc.team3388.actions;

import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.*;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action rotateTurretByVision(TurretSystem turretSystem, VisionSystem visionSystem) {
        double initialTurretAngle = turretSystem.currentValue();

        return Actions.sequential(
                Actions.wait(Time.milliseconds(100)),
                turretSystem.keepAtAction(() -> initialTurretAngle + visionSystem.alignmentError())
        ).requires(turretSystem, visionSystem);
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

    public static Action fullFeedAction(ShooterFeederSystem feederSystem, hopperSystem hopperSystem) {
        return Actions.parallel(
                feederSystem.rotateAction(),
                hopperSystem.rotateAction()
        ).requires(hopperSystem, feederSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
