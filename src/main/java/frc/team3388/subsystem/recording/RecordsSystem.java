package frc.team3388.subsystem.recording;

import com.flash3388.flashlib.robot.scheduling.Subsystem;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RecordsSystem<T> extends Subsystem {
    private final List<Record<T>> records;
    private final Supplier<T> valueSupplier;
    private final int maxRecordsAmount;
    private final Clock clock;

    public RecordsSystem(Supplier<T> valueSupplier, int maxRecordsAmount, Clock clock) {
        this.valueSupplier = valueSupplier;
        this.maxRecordsAmount = maxRecordsAmount;
        this.clock = clock;

        records = new ArrayList<>();
    }

    public void clearRecord() {
        records.clear();
    }

    public void addRecord() {
        if(records.size() == maxRecordsAmount)
            records.remove(0);
        records.add(new Record<>(valueSupplier.get(), clock.currentTime()));
    }

    public T correspondingValue(Time timestamp) {
        for (Record<T> record : records) {
            if(timestamp.before(record.timestamp()))
                return record.get();
        }

        throw new IllegalArgumentException("there is no corresponding record for " + timestamp);
    }
}
