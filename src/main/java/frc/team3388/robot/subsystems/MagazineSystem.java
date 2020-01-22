package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class MagazineSystem extends Subsystem {
    private static final double SPEED = 0.1;
    private SpeedController mController1 , mController2;
    private DigitalInput mDigitalInput;

    public MagazineSystem(SpeedController controller1 , SpeedController controller2 , DigitalInput digitalInput)
    {
        mController1 = controller1;
        mController2 = controller2;
        mDigitalInput = digitalInput;
    }

    public void run()
    {
        mController1.set(SPEED);
        mController2.set(-SPEED);
    }

    public boolean isHigh()
    {
        return mDigitalInput.get();
    }

    public void stop()
    {
        mController2.stopMotor();
        mController1.stopMotor();
    }
}
