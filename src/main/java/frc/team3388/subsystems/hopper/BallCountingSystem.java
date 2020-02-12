package frc.team3388.subsystems.hopper;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.Actions;
import edu.wpi.first.wpilibj.DigitalInput;

import java.util.function.BooleanSupplier;

public class BallCountingSystem extends Subsystem {
    private static final int FEEDER_SENSOR_PORT = 1;
    private static final int INTAKE_SENSOR_PORT = 0;
    private static final int MAX_BALLS_LEGALLY = 5;

    private final int maxBalls;
    private int counter;
    private boolean shooting;

    public BallCountingSystem(BooleanSupplier intakeSupplier, BooleanSupplier feederSupplier, int maxBalls) {
        this.maxBalls = maxBalls;

        counter = 0;
        shooting = false;
        Actions.conditional(intakeSupplier, additionAction());
        Actions.conditional(() -> shooting && !feederSupplier.getAsBoolean(), removalAction());
    }

    public static  BallCountingSystem forRobot() {
        DigitalInput intakeSensor = new DigitalInput(INTAKE_SENSOR_PORT);
        DigitalInput feederSensor = new DigitalInput(FEEDER_SENSOR_PORT);

        return new BallCountingSystem(() -> !intakeSensor.get(), () -> !feederSensor.get(), MAX_BALLS_LEGALLY);
    }

    public Action startShootingAction() {
        return Actions.instantAction(this::startShooting);
    }

    public Action stopShootingAction() {
        return Actions.instantAction(this::stopShooting);
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
