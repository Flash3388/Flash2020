package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.InstantAction;
import frc.team3388.robot.subsystems.ShooterSystem;

public class RotateAction extends InstantAction {
    private final ShooterSystem shooter;

    public RotateAction(ShooterSystem shooter) {
        this.shooter = shooter;
        requires(this.shooter);
    }


    @Override
    protected void execute() {
        //TODO
    }
}
