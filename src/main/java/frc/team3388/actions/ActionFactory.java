package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import frc.team3388.subsystems.time.sync.SyncSystem;

public class ActionFactory {
    public static Action syncAction(SyncSystem syncSystem) {
        return Actions.instantAction(() -> {
            syncSystem.setFirst();
            syncSystem.setSecond();
            syncSystem.unSync();
        });
    }
}
