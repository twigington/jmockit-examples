package examples.reusable;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class InheritanceTests {
    @Mocked
    DeviceHandler handler;

    @Test
    public void mainClass() throws Exception {
        final Device one = new Device("one");

        new NonStrictExpectations() {{
            handler.doSomething(withInstanceOf(Device.class));
            result = 1;
        }};

        final int result = handler.doSomething(one);
        assertThat(result, is(1));
    }

    @Test
    public void subClass() throws Exception {
        final DeviceA oneA = new DeviceA("oneA", false);

        new NonStrictExpectations() {{
            handler.doSomething(withInstanceOf(Device.class));
            result = 1;
        }};

        final int result = handler.doSomething(oneA);
        assertThat(result, is(1));
    }

    @Test
    public void both() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        new NonStrictExpectations() {{
            handler.doSomething(withInstanceOf(DeviceA.class));
            result = 100;

            handler.doSomething(withInstanceOf(Device.class));
            result = 50;
        }};

        final int result = handler.doSomething(oneA);
        assertThat(result, is(100));

        final int result2 = handler.doSomething(one);
        assertThat(result2, is(50));
    }

    @Test
    public void bothFlippedOrderMatters() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        new NonStrictExpectations() {{
            handler.doSomething(withInstanceOf(Device.class));
            result = 50;

            handler.doSomething(withInstanceOf(DeviceA.class));
            result = 100;
        }};

        final int result = handler.doSomething(oneA);
        assertThat(result, is(50));

        final int result2 = handler.doSomething(one);
        assertThat(result2, is(50));
    }

}
