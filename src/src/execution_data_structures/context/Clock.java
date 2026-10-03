package execution_data_structures.context;

import java.time.Instant;

public class Clock {
    public static long epochSecond() {
        return (long) Instant.now().getEpochSecond();
    }

    public static long nanos() {
        return (long) Instant.now().getNano();
    }
}
