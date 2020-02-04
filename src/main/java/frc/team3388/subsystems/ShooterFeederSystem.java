package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.scheduling.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team3388.BallCounter;

import java.util.function.BooleanSupplier;

public class ShooterFeederSystem extends Subsystem {
    private static final int CONTROLLER_PORT = 2;
    private static final int FEEDER_SENSOR_PORT = 1;
    private static final double SPEED = -0.4;
    private final SpeedController controller;
    private final BooleanSupplier hasBallInFeeder;
    private final BallCounter counter;

    public ShooterFeederSystem(SpeedController controller, BooleanSupplier hasBallInFeeder, BallCounter counter) {
        this.controller = controller;
        this.hasBallInFeeder = hasBallInFeeder;
        this.counter = counter;
    }

    public static ShooterFeederSystem standard(BallCounter counter) {
        return new ShooterFeederSystem(new WPI_VictorSPX(CONTROLLER_PORT), () -> !new DigitalInput(FEEDER_SENSOR_PORT).get(), counter);
    }

    public void feed() {
        controller.set(SPEED);
    }

    public void stop() {
        controller.stopMotor();
    }

    public boolean hasBall() {
        return hasBallInFeeder.getAsBoolean();
    }

    public void removeBall() {
        counter.remove();
    }

    public boolean isEmpty() {
        return counter.isEmpty();
    }
}
