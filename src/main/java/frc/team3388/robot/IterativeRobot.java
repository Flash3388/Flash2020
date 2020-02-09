package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.xbox.XboxButton;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3388.action.EngageIntake;
import frc.team3388.subsystems.IntakeSystem;

public class IterativeRobot implements IterativeRobotInterface {
    private final Robot mRobot;
    private  final IntakeSystem intakeSystem;
    private  final XboxController xboxController;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        this.intakeSystem=new IntakeSystem(new  WPI_TalonSRX (RobotMap.INTAKE_MANOA), 
                new DoubleSolenoid(RobotMap.INTAKE_PISTON_FORWARD, RobotMap.INTAKE_PISTON_REVERSE),
                new DoubleSolenoid(RobotMap.INTAKE_PISTON1_FORWARD,RobotMap.INTAKE_PISTON1_REVERSE));
        xboxController =new XboxController(RobotMap.CONTROLLER);
        xboxController.getButton(XboxButton.A).whenPressed(new EngageIntake(intakeSystem));
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
