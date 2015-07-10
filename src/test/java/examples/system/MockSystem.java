package examples.system;

import mockit.Mock;
import mockit.MockUp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 */
public class MockSystem extends MockUp<System> {
    @Mock
    public static long currentTimeMillis() {
        return LocalDateTime.of(2000, 4, 15, 4, 0).toEpochSecond(ZoneOffset.ofHours(0));
    }
}
