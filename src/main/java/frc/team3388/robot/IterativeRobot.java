package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import frc.team3388.robot.subsystems.ClimbSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private ClimbSystem mClimbSystem;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        mClimbSystem = new ClimbSystem(new WPI_TalonSRX(RobotMap.CLIMB_CONTROLLER_PULL), new WPI_TalonSRX(RobotMap.CLIMB_CONTROLLER_RISE));
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
