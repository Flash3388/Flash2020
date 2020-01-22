package frc.team3388.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.function.DoubleSupplier;

public class EncoderSrx implements DoubleSupplier {
    private static final double WHEEL_DIAMETER = 0.1524;
    private static final int PPR = 4096;
    private static final double FINAL_RATIO = Math.PI * WHEEL_DIAMETER * (1.0/PPR);

    private final WPI_TalonSRX controller;
    private final boolean isReversed;

    public EncoderSrx(WPI_TalonSRX controller, boolean isReversed) {
        this.controller = controller;
        this.isReversed = isReversed;
    }

    @Override
    public double getAsDouble() {
        return controller.getSelectedSensorPosition() * FINAL_RATIO * (isReversed ? -1 : 1);
    }

    public void reset() {
        controller.setSelectedSensorPosition(0);
    }
}
