package examples.threadlocal;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class MyServiceTest {
    @Test
    public void noMocking() {
        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is("Default Module"));
    }

    @Test
    public void mockedGetModule(@Mocked("getModule") final MyThreadLocal unused) {
        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations() {{
            MyThreadLocal.getThreadLocalCtx().getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Test
    public void testTwo(@Mocked("getOtherService") final MyThreadLocal unused,
                        @Mocked final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";

        //noinspection UnusedDeclaration
        new Expectations() {{
            MyThreadLocal.getThreadLocalCtx().getOtherService();
            result = otherService;

            otherService.getName();
            result = expectedOtherServiceName;
        }};

        MyService myService = new MyService();

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }
}
