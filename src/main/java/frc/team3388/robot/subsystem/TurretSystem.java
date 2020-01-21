package frc.team3388.robot.subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;

public class TurretSystem extends Subsystem {
    private static final double SPEED = 0.3;
    private WPI_TalonSRX mController;

    public TurretSystem(WPI_TalonSRX controller)
    {
        mController = controller;
    }

    public void turnRight()
    {
        mController.set(SPEED);
    }

    public void turnLeft()
    {
        mController.set(SPEED * -1);
    }

    public void stop()
    {
        mController.stopMotor();
    }
}
