package frc.team3388.action;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.IntakeSystem;

public class ActionFactory {
    public static Action intakeAction(IntakeSystem intakeSystem) {
        return Actions.sequential(
                Actions.runnableAction(intakeSystem::intake),
                Actions.instantAction(intakeSystem::stop)).requires(intakeSystem);
    }

    public static Action unfoldIntakeAction(IntakeSystem intakeSystem) {
        return Actions.instantAction(intakeSystem::unfold).requires(intakeSystem);
    }

    public static Action foldIntakeAction(IntakeSystem intakeSystem) {
        return Actions.instantAction(intakeSystem::fold).requires(intakeSystem);
    }

    public static Action engageIntakeAction(IntakeSystem intakeSystem) {
        return Actions.sequential(
                unfoldIntakeAction(intakeSystem),
                intakeAction(intakeSystem)).requires(intakeSystem);
    }
}
