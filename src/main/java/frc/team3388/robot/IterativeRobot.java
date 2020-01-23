package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.scheduling.triggers.Triggers;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3388.robot.actions.magazine.MoveMagazine;
import frc.team3388.robot.subsystems.MagazineSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private MagazineSystem mMagazineSystem;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        mMagazineSystem = new MagazineSystem(new WPI_TalonSRX(RobotMap.MAGAZINE_FIRST_CONTROLLER), new WPI_TalonSRX(RobotMap.MAGAZINE_SECOND_CONTROLLER),
                new DoubleSolenoid(RobotMap.MAGAZINE_DS_1,RobotMap.MAGAZINE_DS_2));
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
