package examples.system;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Attempting to partially mock System so that time can be fixed.
 */
public class MyObjectTest {
    @Tested
    MyObject myObject;

    /**
     * This seems to works okay, but as you can see it also changes the behaviour of some other methods.
     */
    @Test
    public void fixedTime_FullyMocked(@Mocked final System system) {
        final LocalDateTime ldt = LocalDateTime.of(2000, 4, 15, 4, 0);

        new Expectations() {{
            System.currentTimeMillis();
            result = ldt.toEpochSecond(ZoneOffset.ofHours(0));
        }};

        assertThat("getNow", myObject.getNow(), is(ldt.toEpochSecond(ZoneOffset.ofHours(0))));
        assertThat("getNow", myObject.getNow(), is(ldt.toEpochSecond(ZoneOffset.ofHours(0))));
        assertThat("JAVA_HOME", System.getenv("JAVA_HOME"), is(nullValue()));
        assertThat("Property Size", System.getProperties(), hasKey("java.home"));
        assertThat("line separator", System.lineSeparator(), is(nullValue()));
        System.out.println("Hello World!");
        System.out.println(System.getenv("JAVA_HOME"));
    }

    /**
     * This worked great, but is going way in 1.19.
     */
    @Test
    public void fixedTime_partiallyMocked(@Mocked("currentTimeMillis") final System system) {
        final LocalDateTime ldt = LocalDateTime.of(2000, 4, 15, 4, 0);

        new Expectations() {{
            System.currentTimeMillis();
            result = ldt.toEpochSecond(ZoneOffset.ofHours(0));
        }};

        assertThat("getNow", myObject.getNow(), is(ldt.toEpochSecond(ZoneOffset.ofHours(0))));
        assertThat("getNow", myObject.getNow(), is(ldt.toEpochSecond(ZoneOffset.ofHours(0))));
        assertThat("JAVA_HOME", System.getenv("JAVA_HOME"), is(notNullValue()));
        assertThat("Property Size", System.getProperties(), hasKey("java.home"));
        assertThat("line separator", System.lineSeparator(), not(isEmptyString()));
        System.out.println("Hello World!");
        System.out.println(System.getenv("JAVA_HOME"));
    }
}