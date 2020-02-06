package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.StorageSystem;

public class MoveStorageAction extends Action {
    private final StorageSystem storageSystem;

    public MoveStorageAction(StorageSystem storageSystem) {
        this.storageSystem = storageSystem;
        requires(storageSystem);
    }

    @Override
    protected void execute() {
        storageSystem.move();
    }

    @Override
    protected void end() {
        storageSystem.stop();
    }
}
