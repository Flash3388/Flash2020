package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
