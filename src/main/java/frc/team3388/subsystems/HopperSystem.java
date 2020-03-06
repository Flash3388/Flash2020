package frc.team3388.subsystems;

import com.beans.BooleanProperty;
import com.beans.Property;
import com.beans.properties.SimpleBooleanProperty;
import com.beans.properties.SimpleProperty;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.function.BooleanSupplier;

public class HopperSystem extends ConstantSpeedRotatableSubsystem implements Testable {
    private static final int CONTROLLER_PORT = 8;
    private static final double SPEED = 0.95;
    private final BooleanSupplier isInIntake;
    private int counter;
    private final ColorSensorV3 sensor;

    public HopperSystem(SpeedController controller) {
        super(controller, SPEED);
        sensor = new ColorSensorV3(I2C.Port.kOnboard);
        isInIntake = () -> sensor.getProximity() >= 160;

    }

    public static HopperSystem forRobot() {
        WPI_VictorSPX controller = new WPI_VictorSPX(CONTROLLER_PORT); controller.setInverted(true);
        return new HopperSystem(controller);
    }

    public void reset() {
        counter = 0;
    }

    public Action eatAction(Clock clock) {
        BooleanProperty hasAdded = new SimpleBooleanProperty();
        Property<Time> collectedTime = new SimpleProperty<>();

        return new GenericActionBuilder()
                .onInitialize(() -> hasAdded.setAsBoolean(false))
                .onExecute(() -> {
                    if(isInIntake.getAsBoolean() && counter <= 4) {
                        if(!hasAdded.getAsBoolean()) {
                            counter++;
                            hasAdded.setAsBoolean(true);
                            collectedTime.set(Time.seconds(0));
                        }
                        rotate();
                    }

                    else {
                        if(counter <= 3 && hasAdded.getAsBoolean() && (clock.currentTime().sub(collectedTime.get()).before(Time.milliseconds(20)) || collectedTime.get().equals(Time.seconds(0)))) {
                            if(collectedTime.get().lessThanOrEquals(Time.seconds(0)))
                                collectedTime.set(clock.currentTime());
                            rotate();
                        }

                        else {
                            stop();
                            hasAdded.setAsBoolean(false);
                        }
                    }
                })
                .onEnd(this::stop)
                .runOnEndWhenInterrupted()
                .build();
    }
}
