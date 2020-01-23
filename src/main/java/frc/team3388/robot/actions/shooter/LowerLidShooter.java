package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import frc.team3388.robot.subsystems.Shooter;

public class LowerLidShooter extends InstantAction {
    private Shooter shooter;

    public LowerLidShooter(Shooter shooter)
    {
        this.shooter = shooter;
        requires(this.shooter);
    }

    @Override
    protected void execute() {
        shooter.lowerLid();
    }
}
