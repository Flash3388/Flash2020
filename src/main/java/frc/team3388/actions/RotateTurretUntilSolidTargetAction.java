package frc.team3388.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import frc.team3388.subsystems.TurretSystem;
import frc.team3388.subsystems.VisionSystem;

public class RotateTurretUntilSolidTargetAction extends Action {
    private static final Time TIME_REQUIRED_FOR_SOLID_TARGET = Time.milliseconds(50);

    private final TurretSystem turretSystem;
    private final VisionSystem visionSystem;
    private final Clock clock;
    private Time startTimeInTarget;

    public RotateTurretUntilSolidTargetAction(TurretSystem turretSystem, VisionSystem visionSystem, Clock clock) {
        this.turretSystem = turretSystem;
        this.visionSystem = visionSystem;
        this.clock = clock;

        requires(turretSystem, visionSystem);
    }

    @Override
    protected void initialize() {
        visionSystem.setProcessingMode();
        startTimeInTarget = Time.milliseconds(0);
    }

    @Override
    protected void execute() {
        if(visionSystem.hasFoundTarget()) {
            startTimeInTarget = startTimeInTarget.equals(Time.milliseconds(0)) ? clock.currentTime() : startTimeInTarget;
            turretSystem.rotate(0.1);
        }
        else {
            startTimeInTarget = Time.milliseconds(0);
            turretSystem.rotate(0.15);
        }
    }

    @Override
    protected void end() {
        turretSystem.stop();
        visionSystem.setNormalMode();
    }

    @Override
    protected boolean isFinished() {
        System.out.println(clock.currentTime().sub(startTimeInTarget));
        return startTimeInTarget.after(Time.milliseconds(0)) && clock.currentTime().sub(startTimeInTarget).largerThanOrEquals(TIME_REQUIRED_FOR_SOLID_TARGET);
    }
}
