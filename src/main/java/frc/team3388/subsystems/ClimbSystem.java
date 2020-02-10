package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.function.BooleanSupplier;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem{
    private static final int CONTROLLER_PORT = 4;
    private static final int LOW_INDICATOR_PORT = 4;
    private static final double SPEED = 1;

    private final BooleanSupplier lowIndicator;

    public ClimbSystem(SpeedController controller, BooleanSupplier lowIndicator) {
        super(controller, SPEED);
        this.lowIndicator = lowIndicator;
    }

    public static ClimbSystem forRobot() {
        WPI_TalonSRX liftController = new WPI_TalonSRX(CONTROLLER_PORT);
        BooleanSupplier lowIndicator = () -> !new DigitalInput(LOW_INDICATOR_PORT).get();

        return new ClimbSystem(liftController, lowIndicator);
    }

    public boolean isLow() {
        return lowIndicator.getAsBoolean();
    }
}
