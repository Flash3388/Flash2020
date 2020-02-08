package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.TurretSystem;

import java.util.function.DoubleSupplier;

public class ActionFactory {
    public static Action keepTurretAtAngle(TurretSystem turretSystem, DoubleSupplier angleSupplier) {
        return Actions.runnableAction(() -> turretSystem.rotateTo(angleSupplier.getAsDouble()));
    }
}
