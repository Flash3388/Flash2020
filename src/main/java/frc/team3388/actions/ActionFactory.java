package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.storage.BallCountingSystem;
import frc.team3388.subsystems.storage.ShooterFeederSystem;
import frc.team3388.subsystems.storage.StorageSystem;

import java.util.function.BooleanSupplier;

public class ActionFactory {
    public static Action fullFeedActionUntilEmpty(ShooterFeederSystem feederSystem, StorageSystem storageSystem, BallCountingSystem countingSystem) {
        return onCondition(fullFeedAction(feederSystem, storageSystem), countingSystem::isEmpty).requires(feederSystem, storageSystem, countingSystem);
    }

    public static Action fullFeedAction(ShooterFeederSystem feederSystem, StorageSystem storageSystem) {
        return Actions.parallel(
                feederSystem.rotateAction(),
                storageSystem.rotateAction()
        ).requires(storageSystem, feederSystem);
    }

    public static Action onCondition(Action action, BooleanSupplier condition) {
        return new ConditionalAction(action, condition);
    }
}
