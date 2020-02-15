package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.hopperSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action fullFeedAction(ShooterFeederSystem feederSystem, hopperSystem hopperSystem) {
        return Actions.parallel(
                feederSystem.rotateAction(),
                hopperSystem.rotateAction()
        ).requires(hopperSystem, feederSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
