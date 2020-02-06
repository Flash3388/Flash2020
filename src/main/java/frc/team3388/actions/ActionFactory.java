package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.actions.ParallelActionGroup;
import frc.team3388.subsystems.BallCountingSystem;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.StorageSystem;

public class ActionFactory {
    public static Action fullFeedAction(StorageSystem storageSystem, ShooterFeederSystem feederSystem) {
        return new ParallelActionGroup().add(
                new MoveStorageAction(storageSystem),
                new FeedShooterAction(feederSystem));
    }

    public static Action fullFeedActionUntilEmpty(StorageSystem storageSystem, ShooterFeederSystem feederSystem, BallCountingSystem countingSystem) {
        return Actions.conditional(countingSystem::isEmpty,
                new ParallelActionGroup().add(
                        new MoveStorageAction(storageSystem),
                        new FeedShooterAction(feederSystem)));
    }
}
