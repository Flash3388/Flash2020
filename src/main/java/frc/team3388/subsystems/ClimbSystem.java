package frc.team3388.subsystems;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.scheduling.actions.GenericActionBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import java.util.function.BooleanSupplier;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem {
    private static final int FIRST_CONTROLLER_PORT = 0;
    private static final int SECOND_CONTROLLER_PORT = 1;
    private static final int SENSOR_PORT = 0;
    private static final double ROTATE_SPEED = 0.7;

    private final BooleanSupplier highIndicator;

    public ClimbSystem(SpeedController controller, BooleanSupplier highIndicator) {
        super(controller, ROTATE_SPEED);
        this.highIndicator = highIndicator;
    }

    public static ClimbSystem forRobot() {
        PWMVictorSPX first = new PWMVictorSPX(FIRST_CONTROLLER_PORT);
        PWMVictorSPX second = new PWMVictorSPX(SECOND_CONTROLLER_PORT);
        DigitalInput highSensor = new DigitalInput(SENSOR_PORT);
        BooleanSupplier highIndicator = () -> !highSensor.get();

        return new ClimbSystem(new SpeedControllerGroup(first, second), highIndicator);
    }

    public Action rise() {
        return new GenericActionBuilder()
                .onExecute(this::rotate)
                .onEnd(this::stop)
                .isFinished(highIndicator)
                .build().requires(this);
    }

    public boolean hasRisen() {
        return highIndicator.getAsBoolean();
    }
}
