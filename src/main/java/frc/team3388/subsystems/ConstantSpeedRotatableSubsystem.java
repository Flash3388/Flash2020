package frc.team3388.subsystems;

import com.flash3388.flashlib.frc.robot.io.devices.actuators.FrcSpeedController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.motion.actions.RotateAction;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;

public class ConstantSpeedRotatableSubsystem extends Subsystem implements Rotatable {
    private final SpeedController controller;
    private final double rotateSpeed;

    public ConstantSpeedRotatableSubsystem(edu.wpi.first.wpilibj.SpeedController controller, double rotateSpeed) {
        this(new FrcSpeedController(controller), rotateSpeed);
    }

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
        controller.stop();
    }
}
