package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.BallCountingSystem;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.StorageSystem;

public class ActionFactory {
    public static Action feedShooterAction(ShooterFeederSystem feederSystem) {
        return Actions.sequential(
                Actions.runnableAction(feederSystem::feed),
                Actions.instantAction(feederSystem::stop)).requires(feederSystem);
    }

    public static Action moveStorageAction(StorageSystem storageSystem) {
        return Actions.sequential(
                Actions.runnableAction(storageSystem::move),
                Actions.instantAction(storageSystem::stop)).requires(storageSystem);
    }

    public static Action fullFeedAction(ShooterFeederSystem feederSystem, StorageSystem storageSystem) {
        return Actions.parallel(
                feedShooterAction(feederSystem),
                moveStorageAction(storageSystem)).requires(storageSystem, feederSystem);
    }

    public static Action fullFeedActionUntilEmpty(ShooterFeederSystem feederSystem, StorageSystem storageSystem, BallCountingSystem countingSystem) {
        return Actions.conditional(countingSystem::isEmpty,
                Actions.parallel(
                        feedShooterAction(feederSystem),
                        moveStorageAction(storageSystem))).requires(storageSystem, feederSystem, countingSystem);
    }
}
