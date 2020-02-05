package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import frc.team3388.robot.subsystems.ShooterSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private final ShooterSystem shooter;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        shooter = ShooterSystem.standard();
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
