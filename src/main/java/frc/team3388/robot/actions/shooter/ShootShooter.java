package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.Shooter;

public class ShootShooter extends Action {
    private Shooter shooter;

    public ShootShooter(Shooter shooter)
    {
        this.shooter = shooter;
        requires(this.shooter);
    }

    @Override
    protected void execute() {
        shooter.shoot();
    }

    @Override
    protected void end() {
        shooter.stop();
    }
}
