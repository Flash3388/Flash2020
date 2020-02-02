package frc.team3388.time;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import com.flash3388.flashlib.robot.scheduling.triggers.TriggerState;
import com.flash3388.flashlib.time.Clock;

public class TimeSynchronizer {
    public static void sync(Clock clock) {
        sync(SyncMethod.standard(clock));
    }

    public static void sync(SyncMethod syncMethod) {
        Trigger syncTrigger = new Trigger(TriggerState.ACTIVE);
        syncTrigger.addToScheduler(syncMethod::shouldSync);
        syncTrigger.whenActive(new InstantAction() {
            @Override
            protected void execute() {
                syncMethod.setFirst();
                syncMethod.setSecond();
                syncTrigger.deactivate();
            }
        });
    }
}
