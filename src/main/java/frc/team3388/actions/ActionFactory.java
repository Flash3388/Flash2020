package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.ShooterSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action interpolateShootAction(ShooterSystem shooterSystem, double distance) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::hideLid),
                shooterSystem.keepAtAction(shooterSystem.interpolateRpm(distance))
        );
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
