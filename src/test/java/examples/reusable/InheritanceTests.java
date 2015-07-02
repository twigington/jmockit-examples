package examples.reusable;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Examples of inheritance tests using the classes from the reusable package.
 * The only mock reuse this shows is that an instance variable can be used in all tests.
 */
public class InheritanceTests {
    @Mocked
    DeviceHandler handler;

    @Test
    public void mainClass() throws Exception {
        final Device one = new Device("one");

        new Expectations() {{
            handler.doSomething(withInstanceOf(Device.class));
            result = 1;
        }};

        final int result = handler.doSomething(one);
        assertThat(result, is(1));
    }

    @Test
    public void subClass() throws Exception {
        final DeviceA oneA = new DeviceA("oneA", false);

        new Expectations() {{
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

        new Expectations() {{
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

        // Using Expectations will complain that the second call isn't made.
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
