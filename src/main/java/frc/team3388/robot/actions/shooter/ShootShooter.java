package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.Shooter;

public class ShootShooter extends Action {
    private Shooter shooter;
    private double speed;

    public ShootShooter(Shooter shooter, double speed)
    {
        this.shooter = shooter;
        this.speed = speed;
        requires(this.shooter);
    }

    @Override
    protected void execute() {
        shooter.shoot(speed);
    }

    @Override
    protected void end() {
        shooter.stop();
    }
}
