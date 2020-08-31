package frc.team3388.actions;

public enum TurretPosition {
    RIGHT(90),
    FRONT(0),
    LEFT(-90);

    private final double value;

    TurretPosition(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
