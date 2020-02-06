package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.ShooterFeederSystem;

public class FeedShooterAction extends Action {
    private final ShooterFeederSystem feeder;

    public FeedShooterAction(ShooterFeederSystem feeder) {
        this.feeder = feeder;
        requires(feeder);
    }

    @Override
    protected void execute() {
        feeder.feed();
    }

    @Override
    protected void end() {
        feeder.stop();
    }
}
