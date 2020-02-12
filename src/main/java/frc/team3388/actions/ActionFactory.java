package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.hopper.BallCountingSystem;
import frc.team3388.subsystems.hopper.ShooterFeederSystem;
import frc.team3388.subsystems.hopper.hopperSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action fullFeedActionUntilEmpty(ShooterFeederSystem feederSystem, hopperSystem hopperSystem, BallCountingSystem countingSystem) {
        return onCondition(fullFeedAction(feederSystem, hopperSystem), countingSystem::isEmpty).requires(feederSystem, hopperSystem, countingSystem);
    }

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
