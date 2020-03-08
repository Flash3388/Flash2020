package frc.team3388.actions;

import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.*;

public class AutonomousActionFactory {
    public static Action rightSideAutonomous(DriveSystem driveSystem, IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, TurretSystem turretSystem, ShooterSystem shooterSystem, VisionSystem visionSystem, Clock clock) {
        Action driveAction = driveFromInitiationLineToControlPanel(driveSystem, clock);

        return Actions.sequential(
                ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, () -> TurretPosition.FRONT).setTimeout(Time.seconds(4)),
                ActionFactory.parallel(
                        driveAction,
                        ActionFactory.until(ActionFactory.fullIntakeAction(intakeSystem, hopperSystem), () -> !driveAction.isRunning())
                ),
//                driveFromControlPanelToInitiationLine(driveSystem, clock),
                ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, () -> TurretPosition.FRONT).setTimeout(Time.seconds(3))
        ).requires(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem);
    }

    public static Action driveFromInitiationLineToControlPanel(DriveSystem driveSystem, Clock clock) {
        return driveSystem.autonomousForwardDrive(clock, new Waypoint(), new Waypoint(3, 0, 0));
    }

    public static Action driveFromControlPanelToInitiationLine(DriveSystem driveSystem, Clock clock) {
        return driveSystem.autonomousBackwardDrive(clock, new Waypoint(0, 0, Math.toRadians(180)), new Waypoint(-3, 0, Math.toRadians(180)));
    }

    public static Action genericAuto(DriveSystem driveSystem, IntakeSystem intakeSystem, HopperSystem hopperSystem, FeederSystem feederSystem, TurretSystem turretSystem, ShooterSystem shooterSystem, VisionSystem visionSystem, Clock clock) {
        return Actions.sequential(
                ActionFactory.visionShootAction(intakeSystem, hopperSystem, feederSystem, turretSystem, shooterSystem, visionSystem, () -> TurretPosition.FRONT).setTimeout(Time.seconds(6.5)),
                Actions.periodic(() -> driveSystem.move(0.35), Time.seconds(3)).requires(driveSystem)
//                driveBackwardsTwoMeters(driveSystem, clock)
        );
    }

    public static Action driveBackwardsTwoMeters(DriveSystem driveSystem, Clock clock) {
        return driveSystem.autonomousBackwardDrive(clock, new Waypoint(0, 0, Math.toRadians(180)), new Waypoint(-2, 0, Math.toRadians(180)));
    }
}
