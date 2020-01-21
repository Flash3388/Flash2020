package frc.team3388.robot.actions.turret;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.TurretSystem;

public class TurretRotateRight extends Action {
    private TurretSystem mTurretSystem;

    public TurretRotateRight(TurretSystem turretSystem)
    {
        mTurretSystem = turretSystem;
        requires(mTurretSystem);
    }

    @Override
    protected void execute() {
        mTurretSystem.turnRight();
    }

    @Override
    protected void end() {
        mTurretSystem.stop();
    }
}
