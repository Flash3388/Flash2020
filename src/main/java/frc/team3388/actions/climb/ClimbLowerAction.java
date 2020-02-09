package frc.team3388.actions.climb;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.ClimbSystem;

public class ClimbLowerAction extends Action {
    private ClimbSystem mClimb;
    private static final double LOWER_TIMEOUT = 1.5;


    public ClimbLowerAction(ClimbSystem climb)
    {
        mClimb = climb;
        requires(mClimb);
        setTimeout(Time.seconds(LOWER_TIMEOUT));
    }

    @Override
    protected void execute() {
        mClimb.lower();
    }

    @Override
    protected void end() {
        mClimb.stopHookLift();
    }
}
