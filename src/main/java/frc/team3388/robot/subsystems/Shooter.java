package frc.team3388.robot.subsystems;

import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.robot.EncoderSrx;

public class Shooter extends Subsystem implements Rotatable {
    private SpeedController speedController;
    private DoubleSolenoid doubleSolenoid;
    private EncoderSrx encoderSrx;

    public Shooter(SpeedController speedController, DoubleSolenoid doubleSolenoid, EncoderSrx encoderSrx) {
        this.speedController = speedController;
        this.doubleSolenoid = doubleSolenoid;
        this.encoderSrx = encoderSrx;
    }

    public void shoot(double rpm) {
        //TODO
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


    @Override
    public void rotate(double speed) {
        //TODO
    }
}
