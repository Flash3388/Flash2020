package frc.team3388.actions.storage;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.StorageSystem;

public class FeedFeederAction extends Action {
    private final StorageSystem storageSystem;
    private final ShooterFeederSystem feeder;

    public FeedFeederAction(StorageSystem storageSystem, ShooterFeederSystem feeder) {
        this.storageSystem = storageSystem;
        this.feeder = feeder;
    }

    @Override
    protected void execute() {
        storageSystem.move();
    }

    @Override
    protected void end() {
        storageSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return feeder.hasBall();
    }
}
