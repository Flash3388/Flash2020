package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class MagazineSystem extends Subsystem {
    private static final double SPEED = 0.1;
    private SpeedController mController1 , mController2;
    private DoubleSolenoid mDoubleSolenoid;

    public MagazineSystem(SpeedController controller1, SpeedController controller2, DoubleSolenoid doubleSolenoid)
    {
        mController1 = controller1;
        mController2 = controller2;
        mDoubleSolenoid = doubleSolenoid;
    }

    public void openPiston()
    {
        mDoubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void closePiston()
    {
        mDoubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void run()
    {
        mController1.set(SPEED);
        mController2.set(-SPEED);
    }

    public void stop()
    {
        mController2.stopMotor();
        mController1.stopMotor();
    }
}
