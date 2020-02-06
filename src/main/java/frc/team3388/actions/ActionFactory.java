package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.BallCountingSystem;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.StorageSystem;

public class ActionFactory {
    public static Action fullFeedAction(StorageSystem storageSystem, ShooterFeederSystem feederSystem) {
        return Actions.parallel(
                new MoveStorageAction(storageSystem),
                new FeedShooterAction(feederSystem));
    }

    public static Action fullFeedActionUntilEmpty(StorageSystem storageSystem, ShooterFeederSystem feederSystem, BallCountingSystem countingSystem) {
        return Actions.conditional(countingSystem::isEmpty,
                Actions.parallel(
                        new MoveStorageAction(storageSystem),
                        new FeedShooterAction(feederSystem)));
    }
}
