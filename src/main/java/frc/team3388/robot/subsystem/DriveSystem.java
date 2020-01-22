package frc.team3388.robot.subsystem;

import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.io.devices.sensors.Encoder;
import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.team3388.robot.EncoderSrx;

public class DriveSystem extends TankDriveSystem {
    private final Gyro gyro;
    private final EncoderSrx encoderLeft;
    private final EncoderSrx encoderRight;
    public DriveSystem(SpeedController rightController, SpeedController leftController, Gyro gyro, EncoderSrx encoderRight, EncoderSrx encoderLeft) {
        super(rightController, leftController);
        this.encoderLeft = encoderLeft;
        this.encoderRight = encoderRight;
        this.gyro = gyro;
    }
    public double getAngle(){
        return gyro.getAngle();
    }
    public double getDistanceRight(){
        return encoderRight.getAsDouble();
    }
    public double getDistanceLeft(){
        return encoderLeft.getAsDouble();
    }
}
