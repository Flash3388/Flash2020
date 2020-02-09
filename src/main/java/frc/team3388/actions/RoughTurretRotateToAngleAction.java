package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.jmath.ExtendedMath;
import frc.team3388.subsystems.TurretSystem;

public class RoughTurretRotateToAngleAction extends Action {
    private static final double DEFAULT_DELTA = 0.5;

    private final TurretSystem turretSystem;
    private final double target;

    public RoughTurretRotateToAngleAction(TurretSystem turretSystem, double target) {
        this.turretSystem = turretSystem;
        this.target = target;
    }

    @Override
    protected void execute() {
        turretSystem.rotateTo(target);
    }

    @Override
    protected void end() {
        turretSystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return ExtendedMath.equals(turretSystem.getAngle(), target, DEFAULT_DELTA);
    }
}
