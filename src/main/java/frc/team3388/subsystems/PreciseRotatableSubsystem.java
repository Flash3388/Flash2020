package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.io.devices.actuators.SpeedController;
import com.flash3388.flashlib.robot.motion.Rotatable;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.team3388.actions.ActionFactory;

import java.util.function.DoubleSupplier;

public class PreciseRotatableSubsystem extends Subsystem implements Rotatable, Testable {
    private final SpeedController controller;
    private final DoubleSupplier valueSupplier;
    private final PIDController pidController;

    public PreciseRotatableSubsystem(SpeedController controller, DoubleSupplier valueSupplier, PIDController pidController, double targetTolerance) {
        this.controller = controller;
        this.valueSupplier = valueSupplier;
        this.pidController = pidController;
        this.pidController.setTolerance(targetTolerance);
    }

    public Action roughRotateToAction(DoubleSupplier target) {
        return ActionFactory.onCondition(keepAtAction(target), pidController::atSetpoint);
    }

    public Action keepAtAction(DoubleSupplier target) {
        return new GenericActionBuilder()
                .onInitialize(this::resetPidController)
                .onExecute(() -> rotateTo(target))
                .onEnd(this::stop)
                .runOnEndWhenInterrupted()
                .build().requires(this);
    }

    public void resetPidController() {
        pidController.reset();
    }

    public double currentValue() {
        return valueSupplier.getAsDouble();
    }

    public void rotateTo(DoubleSupplier target) {
        rotate(pidController.calculate(currentValue(), target.getAsDouble()));
    }

    @Override
    public void rotate(double speed) {
        controller.set(speed);
    }

    @Override
    public void stop() {
        controller.stop();
        controller.set(0);
    }
}
