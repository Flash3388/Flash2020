package frc.team3388.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.robot.EncoderSrx;

public class Shooter extends Subsystem {
    private WPI_TalonSRX speedController;
    private DoubleSolenoid doubleSolenoid;
    private EncoderSrx encoderSrx;

    public Shooter(WPI_TalonSRX speedController, DoubleSolenoid doubleSolenoid, EncoderSrx encoderSrx)
    {
        this.speedController = speedController;
        this.doubleSolenoid = doubleSolenoid;
        this.encoderSrx = encoderSrx;
    }

    public void shoot(double speed)
    {
        speedController.set(speed);
    }

    public void shootRPM(double rpm)
    {
        speedController.set(ControlMode.Velocity, rpm * 4096 / 600);
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

    public double getSpeed()
    {
        return encoderSrx.getAsDouble();
    }
}
