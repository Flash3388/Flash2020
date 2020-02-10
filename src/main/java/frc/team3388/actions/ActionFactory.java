package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.jmath.ExtendedMath;
import frc.team3388.subsystems.shooter.ShooterAngleAdjustmentSystem;
import frc.team3388.subsystems.shooter.ShooterSystem;

import java.util.function.DoubleSupplier;

public class ActionFactory {
    public static Action adjustThenShootAction(ShooterSystem shooterSystem, ShooterAngleAdjustmentSystem adjustmentSystem, double shootingAngle, double rpm) {
        return Actions.sequential(
                adjustmentSystem.roughRotateToAction(shootingAngle),
                shooterSystem.keepAtAction(rpm)
        ).requires(shooterSystem, adjustmentSystem);
    }

}
