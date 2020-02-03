package frc.team3388.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.robot.EncoderSrx;

public class Shooter extends Subsystem implements Rotatable {
    private static final int SHOOTER_CONTROLLER = 0;
    private static final int DOUBLE_SOLENOID_1 = 1;
    private static final int DOUBLE_SOLENOID_2 = 2;

    private SpeedController speedController;
    private DoubleSolenoid doubleSolenoid;
    private EncoderSrx encoderSrx;

    public Shooter(SpeedController speedController, DoubleSolenoid doubleSolenoid, EncoderSrx encoderSrx) {
        this.speedController = speedController;
        this.doubleSolenoid = doubleSolenoid;
        this.encoderSrx = encoderSrx;
    }

    public static Shooter standard() {
        WPI_TalonSRX speedController = new WPI_TalonSRX(SHOOTER_CONTROLLER);
        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(DOUBLE_SOLENOID_1, DOUBLE_SOLENOID_2);
        EncoderSrx encoderSrx = new EncoderSrx(speedController, false);

        return new Shooter(speedController, doubleSolenoid, encoderSrx);
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
