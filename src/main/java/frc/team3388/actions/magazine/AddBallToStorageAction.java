package frc.team3388.actions.magazine;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.StorageSystem;

public class AddBallToStorageAction extends Action {
    private StorageSystem storageSystem;

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
        return storageSystem.isFull();
    }
}
