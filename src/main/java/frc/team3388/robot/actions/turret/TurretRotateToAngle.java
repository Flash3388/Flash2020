package frc.team3388.robot.actions.turret;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.TurretSystem;

public class TurretRotateToAngle extends Action {
    private TurretSystem mTurretSystem;
    private double mAngle; // -180 to 180
    private boolean isFinished = false;

    public TurretRotateToAngle(TurretSystem turretSystem , double angle)
    {
        mTurretSystem = turretSystem;
        mAngle = angle;
        requires(mTurretSystem);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    protected void execute() {
        double mCurrentAngle = mTurretSystem.getAngle();

        if (mCurrentAngle == mAngle)
            isFinished = true;

        boolean isNegative = (mAngle < 0);

        if (isNegative)
            mTurretSystem.turnLeft();
        else
            mTurretSystem.turnRight();
    }

    @Override
    protected void end() {
        mTurretSystem.stop();
    }
}
