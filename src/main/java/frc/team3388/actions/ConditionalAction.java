package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;

import java.util.function.BooleanSupplier;

public class ConditionalAction extends Action {
    private final Action action;
    private final BooleanSupplier condition;

    public ConditionalAction(Action action, BooleanSupplier condition) {
        this.action = action;
        this.condition = condition;
    }

    @Override
    protected void initialize() {
        action.start();
    }

    @Override
    protected void execute() {
        if(condition.getAsBoolean())
            action.cancel();
    }

    @Override
    protected void end() {
    }

    @Override
    protected boolean isFinished() {
        return !action.isRunning();
    }
}
