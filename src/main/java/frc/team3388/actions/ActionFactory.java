package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.ClimbSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action foldClimbAction(ClimbSystem climbSystem) {
        return onCondition(climbSystem.rotateAction(), climbSystem::isLow).requires(climbSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
