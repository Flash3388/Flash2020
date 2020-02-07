package frc.team3388.actions.turret;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.actions.subsystems.TurretSystem;

public class TurretRotateRight extends Action {
    private TurretSystem turretSystem;

    public TurretRotateRight(TurretSystem turretSystem) {
        this.turretSystem = turretSystem;
        requires(turretSystem);
    }

    @Override
    protected void execute() {
        turretSystem.turnRight();
    }

    @Override
    protected void end() {
        turretSystem.stop();
    }
}
