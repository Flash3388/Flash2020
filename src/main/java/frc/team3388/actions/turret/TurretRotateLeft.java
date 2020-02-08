package frc.team3388.actions.turret;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.TurretSystem;

public class TurretRotateLeft extends Action {
    private TurretSystem turretSystem;

    public TurretRotateLeft(TurretSystem turretSystem) {
        this.turretSystem = turretSystem;
        requires(this.turretSystem);
    }
    @Override
    protected void execute() {
        turretSystem.turnLeft();
    }

    @Override
    protected void end() {
        turretSystem.stop();
    }
}
