package frc.team3388.robot.actions.shooter;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import frc.team3388.robot.subsystems.ShooterSystem;

public class ShootAction extends Action {
    private final ShooterSystem shooter;
    private final double rpm;

    public ShootAction(ShooterSystem shooter, double rpm) {
        this.shooter = shooter;
        this.rpm = rpm;

        requires(this.shooter);
    }

    @Override
    protected void execute() {
        shooter.shoot(rpm);
    }

    @Override
    protected void end() {
        shooter.stop();
    }
}
