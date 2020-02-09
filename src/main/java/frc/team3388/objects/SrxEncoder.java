package frc.team3388.objects;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.function.DoubleSupplier;

public class SrxEncoder implements DoubleSupplier {
    private static final double REVERSE_PULSES_PER_REVOLUTION = 1/4096.0;
    private final double revolutionToUnitRation;
    private final WPI_TalonSRX talon;

    public SrxEncoder(int deviceNumber, double revolutionToUnitRation) {
        this.revolutionToUnitRation = revolutionToUnitRation;
        talon = new WPI_TalonSRX(deviceNumber);
    }

    public static SrxEncoder forDrivetrain(int deviceNumber, double wheelDiameter) {
        return new SrxEncoder(deviceNumber, wheelDiameter * Math.PI);
    }

    @Override
    public double getAsDouble() {
        return talon.getSelectedSensorPosition() * REVERSE_PULSES_PER_REVOLUTION * revolutionToUnitRation;
    }

    public double getVelocityPerSecond() {
        return talon.getSelectedSensorVelocity() * 10 * REVERSE_PULSES_PER_REVOLUTION * revolutionToUnitRation;
    }

    public void reset() {
        talon.setSelectedSensorPosition(0);
    }
}
