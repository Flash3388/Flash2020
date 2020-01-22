package frc.team3388.robot.actions.climb;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.time.Time;
import frc.team3388.robot.subsystems.ClimbSystem;

public class ClimbPullAction extends Action {
    private ClimbSystem mClimb;
    private static final double CLIMB_TIMEOUT = 1.5;

    public ClimbPullAction(ClimbSystem climb)
    {
        mClimb = climb;
        requires(mClimb);
        setTimeout(Time.seconds(CLIMB_TIMEOUT));
    }

    @Override
    protected void execute() {
        mClimb.pull();
    }

    @Override
    protected void end() {
        mClimb.stopPull();
    }
}
