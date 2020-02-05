package frc.team3388.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.robot.EncoderSrx;

public class ShooterSystem extends Subsystem implements Rotatable {
    private static final int SHOOTER_CONTROLLER = 0;

    private SpeedController speedController;
    private EncoderSrx encoderSrx;

    public ShooterSystem(SpeedController speedController, EncoderSrx encoderSrx) {
        this.speedController = speedController;
        this.encoderSrx = encoderSrx;
    }

    public static ShooterSystem standard() {
        WPI_TalonSRX speedController = new WPI_TalonSRX(SHOOTER_CONTROLLER);
        EncoderSrx encoderSrx = new EncoderSrx(speedController, false);

        return new ShooterSystem(speedController, encoderSrx);
    }

    public void shoot(double rpm) {
        //TODO
    }

    public void stop() {

        speedController.set(0);
    }

    public double getSpeed() {

        return encoderSrx.getAsDouble();
    }


    @Override
    public void rotate(double speed) {
        //TODO
    }
}
