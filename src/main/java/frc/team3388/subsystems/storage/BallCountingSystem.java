package frc.team3388.subsystems.storage;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import com.flash3388.flashlib.robot.scheduling.triggers.Trigger;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.function.BooleanSupplier;

public class BallCountingSystem extends Subsystem {
    private static final int FEEDER_SENSOR_PORT = 1;
    private static final int INTAKE_SENSOR_PORT = 0;
    private static final int MAX_BALLS_LEGALLY = 5;

    private final int maxBalls;
    private int counter;
    private boolean shooting;

    public BallCountingSystem(BooleanSupplier feederSupplier, BooleanSupplier intakeSupplier, int maxBalls) {
        this.maxBalls = maxBalls;

        counter = 0;
        shooting = false;
        Actions.conditional(intakeSupplier, additionAction());
        Actions.conditional(() -> shooting && !feederSupplier.getAsBoolean(), removalAction());
    }

    public static  BallCountingSystem forRobot() {
        return new BallCountingSystem(() -> !new DigitalInput(FEEDER_SENSOR_PORT).get(), () -> !new DigitalInput(INTAKE_SENSOR_PORT).get(), MAX_BALLS_LEGALLY);
    }

    public void startShooting() {
        shooting = true;
    }

    public void stopShooting() {
        shooting = false;
    }

    public boolean isFull() {
        return counter >= maxBalls;
    }

    public boolean isEmpty() {
        return counter <= 0;
    }

    private Action additionAction() {
        return Actions.instantAction(() -> counter += counter >= maxBalls ? 0 : 1);
    }

    private Action removalAction() {
        return Actions.instantAction(() -> counter -= counter <= 0 ? 0 : 1);
    }
}
