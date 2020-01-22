package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.hid.xbox.XboxController;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Time;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SPI;
import frc.team3388.robot.actions.turret.TurretRotateLeft;
import frc.team3388.robot.actions.turret.TurretRotateRight;
import frc.team3388.robot.subsystems.TurretSystem;

public class IterativeRobot implements IterativeRobotInterface {

    private final Robot mRobot;
    private TurretSystem mTurretSystem;
    private XboxController mController;

    public IterativeRobot(Robot robot) {
        mRobot = robot;
        mTurretSystem = new TurretSystem(new PWMVictorSPX(RobotMap.TURRET_CONTROLLER) , new ADXRS450_Gyro(SPI.Port.kOnboardCS0));
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
        Actions.periodic(()->System.out.println(mTurretSystem.getAngle()), Time.seconds(1))
                .start();
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
