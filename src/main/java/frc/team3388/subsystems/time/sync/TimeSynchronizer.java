package frc.team3388.subsystems.time.sync;

import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import com.flash3388.flashlib.time.Clock;
import frc.team3388.actions.ActionFactory;

public class TimeSynchronizer {
    public static void sync(Clock clock) {
        sync(SyncSystem.standard(clock));
    }

    public static void sync(SyncSystem syncSystem) {
        Trigger syncTrigger = new Trigger();
        syncTrigger.whenActive(ActionFactory.syncAction(syncSystem));
        syncTrigger.addToScheduler(syncSystem::shouldSync);
    }
}
