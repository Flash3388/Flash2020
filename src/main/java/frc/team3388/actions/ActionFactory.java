package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.storage.BallCountingSystem;
import frc.team3388.subsystems.storage.ShooterFeederSystem;
import frc.team3388.subsystems.storage.StorageSystem;

public class ActionFactory {
    public static Action fullFeedActionUntilEmpty(ShooterFeederSystem feederSystem, StorageSystem storageSystem, BallCountingSystem countingSystem) {
        return Actions.conditional(countingSystem::isEmpty, fullFeedAction(feederSystem, storageSystem)).requires(feederSystem, storageSystem, countingSystem);
    }

    public static Action fullFeedAction(ShooterFeederSystem feederSystem, StorageSystem storageSystem) {
        return Actions.parallel(
                feederSystem.rotateAction(),
                storageSystem.rotateAction()
        ).requires(storageSystem, feederSystem);
    }
}
