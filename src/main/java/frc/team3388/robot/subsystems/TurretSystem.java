package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TurretSystem extends Subsystem {
    private static final double SPEED = 0.3;
    private SpeedController mController;
    private Gyro mGyro;

    public TurretSystem(SpeedController controller, Gyro gyro)
    {
        mController = controller;
        mGyro = gyro;
    }

    public void turnRight()
    {
        mController.set(SPEED);
    }

    public void turnLeft()
    {
        mController.set(SPEED * -1);
    }

    public double getAngle()
    {
        return mGyro.getAngle();
    }

    public void stop()
    {
        mController.stopMotor();
    }
}
