package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.ClimbSystem;

public class ActionFactory {
    public static Action foldClimbAction(ClimbSystem climbSystem) {
        return Actions.conditional(climbSystem::isLow, climbSystem.rotateAction()).requires(climbSystem);
    }
}
