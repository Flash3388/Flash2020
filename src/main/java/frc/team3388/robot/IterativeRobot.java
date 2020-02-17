package frc.team3388.robot;

import com.flash3388.flashlib.frc.robot.IterativeRobotInterface;
import com.flash3388.flashlib.frc.robot.hid.Joystick;
import com.flash3388.flashlib.robot.Robot;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3388.actions.ActionFactory;
import frc.team3388.subsystems.DriveSystem;
import frc.team3388.subsystems.HopperSystem;
import frc.team3388.subsystems.IntakeSystem;
import frc.team3388.subsystems.ShooterFeederSystem;
import frc.team3388.subsystems.ShooterSystem;

public class IterativeRobot implements IterativeRobotInterface {
    private final Joystick right;
    private final Joystick left;

    private final Robot robot;
    private final DriveSystem driveSystem;
    private final IntakeSystem intakeSystem;
    private final HopperSystem hopperSystem;
    private final ShooterFeederSystem feederSystem;
    private final ShooterSystem shooterSystem;
    private final NetworkTableEntry rpm;

    public IterativeRobot(Robot robot) {
        this.robot = robot;
        driveSystem = DriveSystem.forRobot();
        intakeSystem = IntakeSystem.forRobot();
        hopperSystem = HopperSystem.forRobot();
        feederSystem = ShooterFeederSystem.forRobot();
        shooterSystem = ShooterSystem.forRobot();
        rpm = NetworkTableInstance.getDefault().getTable("gay").getEntry("rpm");
        rpm.setDouble(0);


        right = new Joystick(1);
        left = new Joystick(2);

        driveSystem.setDefaultAction(ActionFactory.manualDriveAction(driveSystem, right, left));
        right.getButton(0).whileHeld(ActionFactory.fullShootAction(shooterSystem, feederSystem, hopperSystem,() -> rpm.getDouble(0)));
        left.getButton(0).whileHeld(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem));
//        left.getButton(0).whileHeld(ActionFactory.fullFeedAction(feederSystem, hopperSystem));
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void teleopInit() {
//        shooterSystem.keepAtAction(() -> 3000).start();
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
