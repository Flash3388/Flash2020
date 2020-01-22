package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.xbox.XboxButton;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import frc.team3388.robot.actions.climb.ClimbLowerAction;
import frc.team3388.robot.actions.climb.ClimbPullAction;
import frc.team3388.robot.actions.climb.ClimbRiseAction;
import frc.team3388.robot.subsystems.ClimbSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private ClimbSystem mClimbSystem;
    private XboxController mXbox;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        mClimbSystem = new ClimbSystem(new WPI_TalonSRX(RobotMap.CLIMB_CONTROLLER_PULL), new WPI_TalonSRX(RobotMap.CLIMB_CONTROLLER_RISE));
        mXbox = new XboxController(RobotMap.CLIMB_XBOX_CONTROLLER);

        mXbox.getDPad().getUp().whenPressed(new ClimbRiseAction(mClimbSystem));
        mXbox.getDPad().getDown().whenPressed(new ClimbLowerAction(mClimbSystem));
        mXbox.getButton(XboxButton.Start).whenPressed(new ClimbPullAction(mClimbSystem));
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
