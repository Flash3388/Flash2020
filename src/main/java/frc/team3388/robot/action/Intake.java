package frc.team3388.robot.action;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.IntakeSystem;

public class Intake extends Action

{
    private final IntakeSystem intakeSystem;

    public Intake( IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;

         requires(intakeSystem) ;
    }

    @Override
    protected void execute() {
        intakeSystem.in();
    }

    @Override
    protected void end() {
        intakeSystem.stop();
    }
}
