package examples.system;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * An example of what not to do. Mocking System at the Test instance variable level will mess up test failure output.
 */
public class MyObjectTestPropertyMocking {
    @Tested
    MyObject myObject;
    @Mocked
    System system;

    @Test
    public void fixedTime() {
        final LocalDateTime ldt = LocalDateTime.of(2000, 4, 15, 4, 0);

        new Expectations() {{
            System.currentTimeMillis();
            result = ldt.toEpochSecond(ZoneOffset.ofHours(0));
        }};

        assertThat(myObject.getNow(), is(ldt.toEpochSecond(ZoneOffset.ofHours(0))));
        // Uncomment this next line to see
        //assertThat(myObject.getNow(), is(not(ldt.toEpochSecond(ZoneOffset.ofHours(0)))));
    }

}