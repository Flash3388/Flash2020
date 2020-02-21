package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.subsystems.*;

public class IterativeRobot implements IterativeRobotInterface {
    private final Joystick right;
    private final Joystick left;

    private final Robot robot;
    private final DriveSystem driveSystem;
    private final IntakeSystem intakeSystem;
    private final HopperSystem hopperSystem;
    private final FeederSystem feederSystem;
    private final ShooterSystem shooterSystem;
    private final TurretSystem turretSystem;
    private final ClimbSystem climbSystem;
    private final VisionSystem visionSystem;

    public IterativeRobot(Robot robot) {
        this.robot = robot;
        driveSystem = DriveSystem.forRobot();
        intakeSystem = IntakeSystem.forRobot();
        hopperSystem = HopperSystem.forRobot();
        feederSystem = FeederSystem.forRobot();
        shooterSystem = ShooterSystem.forRobot();
        turretSystem = TurretSystem.forRobot();
        climbSystem = ClimbSystem.forRobot();
        visionSystem = VisionSystem.forRobot();

        right = new Joystick(1);
        left = new Joystick(2);

        driveSystem.setDefaultAction(ActionFactory.manualDriveAction(driveSystem, right, left));
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
