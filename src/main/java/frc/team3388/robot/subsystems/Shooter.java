package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class Shooter extends Subsystem {
    private SpeedController speedController;
    private DoubleSolenoid doubleSolenoid;

    public Shooter(SpeedController speedController, DoubleSolenoid doubleSolenoid)
    {
        this.speedController = speedController;
        this.doubleSolenoid = doubleSolenoid;
    }

    public void shoot(double speed)
    {
        speedController.set(speed);
    }

    public void stop()
    {
        speedController.set(0);
    }

    public void raiseLid()
    {
        doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void lowerLid()
    {
        doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
}
