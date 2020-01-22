package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedControllerGroup;
import com.flash3388.flashlib.robot.io.devices.sensors.Encoder;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.team3388.robot.actions.JoystickDrive;
import frc.team3388.robot.subsystem.DriveSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private final DriveSystem driveSystem;
    private final Joystick right;
    private final Joystick left;
    public IterativeRobot(Robot robot) {
        right = new Joystick(RobotMap.RIGHT_JOYSTICK_CHANNEL);
        left = new Joystick(RobotMap.LEFT_JOYSTICK_CHANNEL);
        WPI_TalonSRX frontright = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_MOTOR);
        WPI_TalonSRX rearright = new WPI_TalonSRX(RobotMap.REAR_RIGHT_MOTOR);

        WPI_TalonSRX rearleft = new WPI_TalonSRX(RobotMap.REAR_LEFT_MOTOR);
        WPI_TalonSRX frontleft = new WPI_TalonSRX(RobotMap.FRONT_LEFT_MOTOR);
        frontleft.setInverted(false);
        rearleft.setInverted(true);
        frontright.setInverted(true);
        rearright.setInverted(true);
        EncoderSrx encoderRight = new EncoderSrx(frontright,true);
        EncoderSrx encoderLeft = new EncoderSrx(rearleft,true);
        Gyro gyro = new ADXRS450_Gyro();
        mRobot = robot;
        driveSystem = new DriveSystem(new SpeedControllerGroup(new FrcSpeedController(frontright), new FrcSpeedController(rearright)),
                new SpeedControllerGroup(new FrcSpeedController(rearleft), new FrcSpeedController(frontleft)), gyro, encoderRight, encoderLeft);
        driveSystem.setDefaultAction(new JoystickDrive(driveSystem, right, left));

    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void robotPeriodic() {
    }
}
