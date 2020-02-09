package frc.team3388.actions.climb;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.ClimbSystem;

public class ClimbRiseAction extends Action {
    private ClimbSystem mClimb;
    private static final double RISE_TIMEOUT = 1.5;

    public ClimbRiseAction(ClimbSystem climb)
    {
        mClimb = climb;
        requires(mClimb);
        setTimeout(Time.seconds(RISE_TIMEOUT));

    }

    @Override
    protected void execute() {
        mClimb.liftHook();
    }

    @Override
    protected void end() {
        mClimb.stopHookLift();
    }
}
