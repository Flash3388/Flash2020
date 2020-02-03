package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3388.robot.subsystems.Shooter;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private final Shooter shooter;

    public IterativeRobot(Robot robot) {
        mRobot = robot;

        WPI_TalonSRX speedController = new WPI_TalonSRX(RobotMap.SHOOTER_CONTROLLER);

        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(RobotMap.DOUBLE_SOLENOID_1, RobotMap.DOUBLE_SOLENOID_2);
        EncoderSrx encoderSrx = new EncoderSrx(speedController, false);
        shooter = new Shooter(speedController, doubleSolenoid, encoderSrx);
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
