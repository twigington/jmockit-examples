package examples.reusable;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class ReusingMocksTests {
    @Mocked
    DeviceHandler handler;

    @Test
    public void reusingMethod() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        reuseMethod();

        assertThat(handler.doSomething(one), is(55));

        assertThat(handler.doSomething(oneA), is(55));
    }

    private void reuseMethod() throws Exception {
        new NonStrictExpectations() {{
            handler.doSomething(withInstanceOf(Device.class));
            result = 55;
        }};
    }

    @Test
    public void reuseInnerClass() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        new InnerReusableExpectations();

        assertThat(handler.doSomething(one), is(100));

        assertThat(handler.doSomething(oneA), is(100));
    }

    final class InnerReusableExpectations extends NonStrictExpectations {
        public InnerReusableExpectations() {
            handler.doSomething(withInstanceOf(Device.class));
            result = 100;
        }
    }

    @Test
    public void reuseNormalClass() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        new ReusableExpectations(handler);

        assertThat(handler.doSomething(one), is(56));

        assertThat(handler.doSomething(oneA), is(56));
    }

    /*
     * An example showing that non-final expectation sub-classes might not get picked up by JMockIt.
     *
     * I've seen it work, but apparently there are limits or doesn't always work, so avoid this and declare them final.
     */
    @Test
    public void reuseNonFinalNormalClassDoesNotWork() throws Exception {
        final Device one = new Device("one");
        final DeviceA oneA = new DeviceA("oneA", false);

        new NonFinalReusableExpectations(handler);

        assertThat(handler.doSomething(one), is(0));

        assertThat(handler.doSomething(oneA), is(0));
    }

}
