package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.Shooter;

public class ShootRPMShooter extends Action {
    private Shooter shooter;
    private double rpm;

    public ShootRPMShooter(Shooter shooter, double rpm)
    {
        this.shooter = shooter;
        this.rpm = rpm;

        requires(this.shooter);
    }

    @Override
    protected void execute() {
        shooter.shootRPM(rpm);
    }

    @Override
    protected void end() {
        shooter.stop();
    }
}
