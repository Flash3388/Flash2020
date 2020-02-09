package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.TurretSystem;

import java.util.function.DoubleSupplier;

public class ActionFactory {
    public static Action keepTurretAtAngle(TurretSystem turretSystem, DoubleSupplier targetSupplier) {
        return Actions.sequential(
                Actions.instantAction(turretSystem::resetPidController),
                Actions.runnableAction(() -> turretSystem.rotateTo(targetSupplier.getAsDouble())),
                Actions.instantAction(turretSystem::stop)
        ).requires(turretSystem);
    }

    public static Action roughTurretRotateAction(TurretSystem turretSystem, double target) {
        return Actions.conditional(() -> turretSystem.hasReachedTarget(target),
                Actions.sequential(
                        Actions.instantAction(turretSystem::resetPidController),
                        Actions.runnableAction(() -> turretSystem.rotateTo(target)),
                        Actions.instantAction(turretSystem::stop))
        ).requires(turretSystem);
    }
}
