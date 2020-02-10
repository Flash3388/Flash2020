package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.control.PidController;
import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.jmath.ExtendedMath;

import java.util.function.DoubleSupplier;

public class PreciseRotatableSubsystem extends Subsystem implements Rotatable {
    private final SpeedController controller;
    private final DoubleSupplier valueSupplier;
    private final PidController pidController;
    private final double targetRoughness;

    public PreciseRotatableSubsystem(SpeedController controller, DoubleSupplier valueSupplier, PidController pidController) {
        this(controller, valueSupplier, pidController, 1, 0);
    }

    public PreciseRotatableSubsystem(SpeedController controller, DoubleSupplier valueSupplier, PidController pidController, double pidLimit, double targetRoughness) {
        this.controller = controller;
        this.valueSupplier = valueSupplier;
        this.pidController = pidController;
        this.targetRoughness = targetRoughness;
        this.pidController.setOutputLimit(pidLimit);
    }

    public Action roughRotateToAction(double target) {
        return Actions.conditional(() -> hasReachedAngle(target), keepAtAction(target)).requires(this);
    }

    public Action keepAtAction(double target) {
        return Actions.sequential(
                Actions.instantAction(this::resetPidController),
                Actions.runnableAction(() -> rotateTo(target)),
                Actions.instantAction(this::stop)
        ).requires(this);
    }

    public void resetPidController() {
        pidController.reset();
    }

    public boolean hasReachedAngle(double target) {
        return ExtendedMath.equals(currentValue(), target, targetRoughness);
    }

    public double currentValue() {
        return valueSupplier.getAsDouble();
    }

    public void rotateTo(double target) {
        rotate(pidController.calculate(currentValue(), target));
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
