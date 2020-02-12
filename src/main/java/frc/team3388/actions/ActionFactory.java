package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.ShooterSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action adjustThenShootAction(ShooterSystem shooterSystem, ShooterAngleAdjustmentSystem adjustmentSystem, double shootingAngle, double rpm) {
        return Actions.sequential(
                adjustmentSystem.roughRotateToAction(shootingAngle),
                shooterSystem.keepAtAction(rpm)
        ).requires(shooterSystem, adjustmentSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
