package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.SequentialActionGroup;
import frc.team3388.actions.storage.FeedFeederAction;
import frc.team3388.actions.storage.FeedShooterAction;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.StorageSystem;

public class ActionFactory {
    public static Action completeFeedAction(StorageSystem storageSystem, ShooterFeederSystem feederSystem) {
        return new SequentialActionGroup().add(
                new FeedFeederAction(storageSystem, feederSystem),
                new FeedShooterAction(feederSystem));
    }
}
