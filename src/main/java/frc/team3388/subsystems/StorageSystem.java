package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.BallCounter;
import frc.team3388.actions.storage.AddBallToStorageAction;

import java.util.function.BooleanSupplier;

public class StorageSystem extends Subsystem {
    private static final int CONTROLLER_PORT = 1;
    private static final int ENTRY_SENSOR_PORT = 0;
    private static final double SPEED = -0.4;
    private final SpeedController controller;
    private final BooleanSupplier hasBallOnEntry;
    private final BallCounter counter;
    private final Trigger intakeTrigger;

    public StorageSystem(SpeedController controller, BooleanSupplier hasBallOnEntry, BallCounter counter) {
        this.controller = controller;
        this.hasBallOnEntry = hasBallOnEntry;
        this.counter = counter;
        intakeTrigger = new Trigger();
        enableTrigger();
    }

    public static StorageSystem standard(BallCounter counter) {
        return new StorageSystem(new WPI_VictorSPX(CONTROLLER_PORT), () -> !new DigitalInput(ENTRY_SENSOR_PORT).get(), counter);
    }

    public void enableTrigger() {
        intakeTrigger.addToScheduler(() -> hasBallOnEntry() && !isFull());
        intakeTrigger.whileActive(new AddBallToStorageAction(this));
    }

    public void disableTrigger() {
        intakeTrigger.addToScheduler(() -> false);
    }

    public void move() {
        controller.set(SPEED);
    }

    public void stop() {
        controller.stopMotor();
    }

    public boolean hasBallOnEntry() {
        return hasBallOnEntry.getAsBoolean();
    }

    public void addBall() {
        counter.add();
    }

    public boolean isFull() {
        return counter.isFull();
    }
}
