package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.function.BooleanSupplier;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem{
    private static final int CONTROLLER_PORT = 4;
    private static final int LOW_INDICATOR_PORT = 4;
    private static final int HIGH_INDICATOR_PORT = 5;
    private static final double SPEED = 1;

    private final BooleanSupplier lowIndicator;
    private final BooleanSupplier highIndicator;

    public ClimbSystem(SpeedController controller, BooleanSupplier lowIndicator, BooleanSupplier highIndicator) {
        super(controller, SPEED);
        this.lowIndicator = lowIndicator;
        this.highIndicator = highIndicator;
    }

    public static ClimbSystem forRobot() {
        WPI_TalonSRX liftController = new WPI_TalonSRX(CONTROLLER_PORT);
        BooleanSupplier lowIndicator = () -> !new DigitalInput(LOW_INDICATOR_PORT).get();
        BooleanSupplier highIndicator = () -> !new DigitalInput(HIGH_INDICATOR_PORT).get();

        return new ClimbSystem(liftController, lowIndicator, highIndicator);
    }

    public boolean isLow() {
        return lowIndicator.getAsBoolean();
    }

    public boolean isHigh() {
        return highIndicator.getAsBoolean();
    }
}
