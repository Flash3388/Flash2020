package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;

public class ActionFactory {
    public static Action percentageShootAction(ShooterSystem shooterSystem, double percentage) {
        return Actions.sequential(
                Actions.runnableAction(() -> shooterSystem.rotate(percentage)),
                Actions.instantAction(shooterSystem::stop)
        ).requires(shooterSystem);
    }

    public static Action rpmShootAction(ShooterSystem shooterSystem, double rpm) {
        return Actions.sequential(
                Actions.instantAction(shooterSystem::resetPidController),
                Actions.runnableAction(() -> shooterSystem.rotateAt(rpm)),
                Actions.instantAction(shooterSystem::stop)
        ).requires(shooterSystem);
    }
}
