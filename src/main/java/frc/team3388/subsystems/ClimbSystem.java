package frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import java.util.function.BooleanSupplier;

public class ClimbSystem extends ConstantSpeedRotatableSubsystem{
    private static final int FIRST_CONTROLLER_PORT = 0;
    private static final int SECOND_CONTROLLER_PORT = 1;
    private static final int HIGH_INDICATOR_PORT = 4;
    private static final double SPEED = 1;

    private final BooleanSupplier highIndicator;

    public ClimbSystem(SpeedController firstController, SpeedController secondController, BooleanSupplier highIndicator) {
        super(new SpeedControllerGroup(firstController, secondController), SPEED);
        this.highIndicator = highIndicator;
    }

    public static ClimbSystem forRobot() {
        WPI_TalonSRX firstController = new WPI_TalonSRX(FIRST_CONTROLLER_PORT);
        WPI_TalonSRX secondController = new WPI_TalonSRX(SECOND_CONTROLLER_PORT);
        DigitalInput highIndicator = new DigitalInput(HIGH_INDICATOR_PORT);

        return new ClimbSystem(firstController, secondController, () -> !highIndicator.get());
    }

    public boolean isAtMaxHight() {
        return highIndicator.getAsBoolean();
    }
}
