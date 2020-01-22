package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;

public class Shooter extends Subsystem {
    private SpeedController speedController;

    public Shooter(SpeedController speedController)
    {
        this.speedController = speedController;
    }

    public void shoot()
    {
        speedController.set(1.0);
    }

    public void stop()
    {
        speedController.set(0);
    }

    public void addLid()
    {

    }

    public void removeLid()
    {

    }
}
