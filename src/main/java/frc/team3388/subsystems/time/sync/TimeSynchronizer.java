package frc.team3388.subsystems.time.sync;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import com.flash3388.flashlib.robot.scheduling.triggers.TriggerState;
import com.flash3388.flashlib.time.Clock;

public class TimeSynchronizer {
    public static void sync(Clock clock) {
        sync(SyncSystem.standard(clock));
    }

    public static void sync(SyncSystem syncSystem) {
        Trigger syncTrigger = new Trigger(TriggerState.ACTIVE);
        syncTrigger.addToScheduler(syncSystem::shouldSync);
        syncTrigger.whenActive(new InstantAction() {
            @Override
            protected void execute() {
                syncSystem.setFirst();
                syncSystem.setSecond();
                syncTrigger.deactivate();
                syncSystem.unSync();
            }
        });
    }
}
