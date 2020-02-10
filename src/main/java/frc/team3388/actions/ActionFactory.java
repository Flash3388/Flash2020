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
                roughShooterAngleAdjust(adjustmentSystem, shootingAngle),
                rpmShootAction(shooterSystem, rpm)
        ).requires(shooterSystem, adjustmentSystem);
    }

    public static Action roughShooterAngleAdjust(ShooterAngleAdjustmentSystem adjustmentSystem, double target) {
        return Actions.sequential(
                Actions.instantAction(adjustmentSystem::resetPidController),
                Actions.conditional(() -> adjustmentSystem.hasReachedAngle(target), Actions.runnableAction(() -> adjustmentSystem.rotateTo(target))),
                Actions.instantAction(adjustmentSystem::stop)
        ).requires(adjustmentSystem);
    }

    public static Action rpmShootAction(ShooterSystem shooterSystem, double rpm) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::resetPidController),
                Actions.runnableAction(() -> shooterSystem.rotateAt(rpm)),
                Actions.instantAction(shooterSystem::stop)
        ).requires(shooterSystem);
    }
}
