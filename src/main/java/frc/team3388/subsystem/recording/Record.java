package frc.team3388.subsystem.recording;

import com.flash3388.flashlib.time.Time;

import java.util.function.Supplier;

public class Record<T> implements Supplier<T>, Comparable<Time> {
    private final T value;
    private final Time timestamp;

    public Record(T value, Time timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public Time timestamp() {
        return timestamp;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public int compareTo(Time time) {
        return timestamp.compareTo(time);
    }
}
