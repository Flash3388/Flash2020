package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.TurretSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action rotateTurretUntil(TurretSystem turretSystem, double target, double speed) {
        return onCondition(Actions.sequential(
                Actions.runnableAction(() -> turretSystem.rotate(speed)),
                Actions.instantAction(turretSystem::stop)), () -> turretSystem.hasReachedAngle(target)
        ).requires(turretSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
