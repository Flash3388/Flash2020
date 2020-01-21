package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.team3388.robot.actions.turret.TurretRotateLeft;
import frc.team3388.robot.actions.turret.TurretRotateRight;
import frc.team3388.robot.subsystems.TurretSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private TurretSystem mTurretSystem;
    private XboxController mController;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        mTurretSystem = new TurretSystem(new WPI_TalonSRX(RobotMap.TURRET_CONTROLLER) , new AnalogGyro(RobotMap.TURRET_GYRO));
        mController = new XboxController(0);

        mController.getDPad().getRight().whileHeld(new TurretRotateRight(mTurretSystem));
        mController.getDPad().getLeft().whileHeld(new TurretRotateLeft(mTurretSystem));
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
