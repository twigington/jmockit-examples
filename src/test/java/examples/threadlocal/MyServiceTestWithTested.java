package examples.threadlocal;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Annotate MyService with @Tested and test MyThreadLocal mocking.
 */
public class MyServiceTestWithTested {
    @Tested MyService myService;

    /**
     * This appears to be the best solution.
     */
    @Test
    public void mockUpMyThreadLocal_otherServiceMock(@Mocked final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";

        new MockUp<MyThreadLocal>() {
            @Mock OtherService getOtherService() {
                return otherService;
            }
        };

        //noinspection UnusedDeclaration
        new Expectations() {{
            otherService.getName();
            result = expectedOtherServiceName;
        }};

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }

    @Test
    public void noMyThreadLocalMock_otherServiceMock(@Mocked final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";
        MyThreadLocal.getThreadLocalCtx().init(otherService);

        //noinspection UnusedDeclaration
        new Expectations() {{
            otherService.getName();
            result = expectedOtherServiceName;
        }};

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicPartialMyThreadLocalMock_otherServiceMock(@Injectable final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";
        final MyThreadLocal myThreadLocal = MyThreadLocal.getThreadLocalCtx();

        new Expectations(myThreadLocal) {{
            MyThreadLocal.getThreadLocalCtx().getOtherService();
            result = otherService;

            otherService.getName();
            result = expectedOtherServiceName;
        }};

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }
}
