package frc.team3388.actions.storage;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.subsystems.ShooterFeederSystem;

public class FeedShooterAction extends Action {
    private final ShooterFeederSystem feeder;

    public FeedShooterAction(ShooterFeederSystem feeder) {
        this.feeder = feeder;
        requires(feeder);
    }

    @Override
    protected void initialize() {
        feeder.removeBall();
    }

    @Override
    protected void execute() {
        feeder.feed();
    }

    @Override
    protected void end() {
        feeder.stop();
    }

    @Override
    protected boolean isFinished() {
        return !feeder.hasBall() && feeder.isEmpty();
    }
}
