package frc.team3388.action;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import frc.team3388.subsystems.IntakeSystem;

public class EngageIntake extends InstantAction {
    private final IntakeSystem intakeSystem;

    public EngageIntake(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;
        requires(intakeSystem);
    }

    @Override
    protected void execute() {
        intakeSystem.fold();
    }
}
