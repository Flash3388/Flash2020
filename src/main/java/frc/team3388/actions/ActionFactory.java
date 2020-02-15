package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.TurretSystem;
import frc.team3388.subsystems.VisionSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action rotateTurretByVision(TurretSystem turretSystem, VisionSystem visionSystem) {
        double initialTurretAngle = turretSystem.currentValue();

        return Actions.sequential(
                Actions.wait(Time.milliseconds(100)),
                turretSystem.keepAtAction(() -> initialTurretAngle + visionSystem.alignmentError())
        ).requires(turretSystem, visionSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
