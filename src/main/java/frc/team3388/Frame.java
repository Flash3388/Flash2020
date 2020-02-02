package frc.team3388;

import com.flash3388.flashlib.time.Time;

public class Frame {
    private final double alignmentError;
    private final double distance;
    private final Time timestamp;

    public Frame(double alignmentError, double distance, Time timestamp) {
        this.alignmentError = alignmentError;
        this.distance = distance;
        this.timestamp = timestamp;
    }

    public double alignmentError() {
        return alignmentError;
    }

    public double distance() {
        return distance;
    }

    public Time timestamp() {
        return timestamp;
    }
}
