package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class ClimbSystem extends Subsystem {
    private static final double RISE_SPEED = 0.1;
    private static final double SPEED_PULL = 0.1;

    private SpeedController mPullController;
    private SpeedController mRiseController;

    public ClimbSystem(SpeedController pullController , SpeedController riseController)
    {
        mPullController = pullController;
        mRiseController = riseController;
    }

    public void pull()
    {
        mPullController.set(SPEED_PULL);
    }

    public void lower()
    {
        mPullController.set(-SPEED_PULL);
    }

    public void rise()
    {
        mRiseController.set(RISE_SPEED);
    }

    public void stopPull()
    {
        mPullController.stopMotor();
    }

    public void stopRise()
    {
        mRiseController.stopMotor();
    }
}
