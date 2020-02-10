package frc.team3388.subsystems.storage;

import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import edu.wpi.first.wpilibj.SpeedController;

public class ConstantSpeedRotatableSubsystem extends Subsystem implements Rotatable {
    private final SpeedController controller;
    private final double rotateSpeed;

    public ConstantSpeedRotatableSubsystem(SpeedController controller, double rotateSpeed) {
        this.controller = controller;
        this.rotateSpeed = rotateSpeed;
    }

    public Action rotateAction() {
        return new RotateAction(this, () -> rotateSpeed);
    }

    public void rotate() {
        rotate(rotateSpeed);
    }

    @Override
    public void rotate(double speed) {
        controller.set(speed);
    }

    @Override
    public void stop() {
        controller.stopMotor();
    }
}
