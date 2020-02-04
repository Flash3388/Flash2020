package frc.team3388.actions.storage;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.StorageSystem;

public class AddBallToStorageAction extends Action {
    private final StorageSystem storageSystem;

    public AddBallToStorageAction(StorageSystem storageSystem) {
        this.storageSystem = storageSystem;
        requires(storageSystem);
    }

    @Override
    protected void initialize() {
        storageSystem.addBall();
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
        return !storageSystem.hasBallOnEntry() && storageSystem.isFull();
    }
}
