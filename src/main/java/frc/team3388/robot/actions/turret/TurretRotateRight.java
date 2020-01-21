package frc.team3388.robot.actions.turret;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.TurretSystem;

public class TurretRotateRight extends Action {
    private TurretSystem mTurretSystem;
    private boolean isFinished = false;

    public TurretRotateRight(TurretSystem turretSystem)
    {
        mTurretSystem = turretSystem;
        requires(mTurretSystem);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void initialize() {
        isFinished = false;
    }

    @Override
    protected void execute() {

        if (!mTurretSystem.canRotate())
        {
            isFinished = true;
            return;
        }

        mTurretSystem.turnRight();
    }

    @Override
    protected void end() {
        mTurretSystem.stop();
    }
}
