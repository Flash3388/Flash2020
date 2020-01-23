package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.Button;
import com.flash3388.flashlib.robot.hid.xbox.XboxButton;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.robot.actions.shooter.ShootShooter;
import frc.team3388.robot.subsystems.Shooter;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private final Shooter shooter;
    private final XboxController xboxController;

    public IterativeRobot(Robot robot) {
        mRobot = robot;

        WPI_TalonSRX speedController = new WPI_TalonSRX(RobotMap.SHOOTER_CONTROLLER);
        configSpeedController(speedController);

        DoubleSolenoid doubleSolenoid = new DoubleSolenoid(RobotMap.DOUBLE_SOLENOID_1, RobotMap.DOUBLE_SOLENOID_2);
        EncoderSrx encoderSrx = new EncoderSrx(speedController, false);
        shooter = new Shooter(speedController, doubleSolenoid, encoderSrx);

        xboxController = new XboxController(0);
        Button button = xboxController.getButton(XboxButton.A);

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

    private void configSpeedController(WPI_TalonSRX speedController)
    {
        speedController.configFactoryDefault();

        /* Config sensor used for Primary PID [Velocity] */
        speedController.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        /**
         * Phase sensor accordingly.
         * Positive Sensor Reading should match Green (blinking) Leds on Talon
         */
        speedController.setSensorPhase(true);

        /* Config the peak and nominal outputs */
        speedController.configNominalOutputForward(0, Constants.kTimeoutMs);
        speedController.configNominalOutputReverse(0, Constants.kTimeoutMs);
        speedController.configPeakOutputForward(1, Constants.kTimeoutMs);
        speedController.configPeakOutputReverse(-1, Constants.kTimeoutMs);

        /* Config the Velocity closed loop gains in slot0 */
        speedController.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
        speedController.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
        speedController.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
        speedController.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
    }
}
