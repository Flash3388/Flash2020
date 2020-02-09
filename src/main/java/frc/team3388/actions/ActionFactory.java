package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.TurretSystem;

import java.util.function.DoubleSupplier;

public class ActionFactory {
    private static final double TURRET_ROTATE_MODIFIER = 0.5;

    public static Action keepTurretAtAngle(TurretSystem turretSystem, DoubleSupplier angleSupplier) {
        return Actions.sequential(
                Actions.instantAction(turretSystem::resetPidController),
                Actions.runnableAction(() -> turretSystem.rotateTo(angleSupplier.getAsDouble())));
    }

    public static Action manualTurretRotateAction(TurretSystem turretSystem, DoubleSupplier rotateValueSupplier) {
        return Actions.runnableAction(() -> turretSystem.rotate(rotateValueSupplier.getAsDouble() * TURRET_ROTATE_MODIFIER));
    }
}
