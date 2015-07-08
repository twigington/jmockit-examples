package examples.threadlocal;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class MyServiceTestWithTested {
    @Tested MyService myService;

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicallyMockedGetModule_withTested() {
        final MyThreadLocal myThreadLocal = MyThreadLocal.getThreadLocalCtx();

        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations(MyThreadLocal.class) {{
            myThreadLocal.getModule();
            result = expectedModule;
        }};

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Test
    public void otherServiceMock(@Mocked final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";
        MyThreadLocal.getThreadLocalCtx().init(otherService);

        //noinspection UnusedDeclaration
        new Expectations() {{
            otherService.getName();
            result = expectedOtherServiceName;
        }};

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }
}
