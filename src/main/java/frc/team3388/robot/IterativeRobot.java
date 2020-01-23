package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.AxisButton;
import com.flash3388.flashlib.robot.hid.Button;
import com.flash3388.flashlib.robot.hid.scheduling.HidScheduling;
import com.flash3388.flashlib.robot.hid.xbox.XboxAxis;
import com.flash3388.flashlib.robot.hid.xbox.XboxButton;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.team3388.robot.actions.shooter.ShootShooter;
import frc.team3388.robot.subsystems.Shooter;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private final Shooter shooter;
    private final XboxController xboxController;

    public IterativeRobot(Robot robot) {
        mRobot = robot;

        PWMVictorSPX speedController = new PWMVictorSPX(RobotMap.SHOOTER_CONTROLLER);
        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(RobotMap.DOUBLE_SOLENOID_1, RobotMap.DOUBLE_SOLENOID_2);
        shooter = new Shooter(speedController, doubleSolenoid);

        xboxController = new XboxController(0);
        Button button = xboxController.getButton(XboxButton.A);
        //AxisButton button = new AxisButton(mRobot.getClock(), xboxController.getAxis(XboxAxis.RT), 0.5);
        //button.addToScheduler(button);

        button.whileHeld(new ShootShooter(shooter, -0.5));
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
