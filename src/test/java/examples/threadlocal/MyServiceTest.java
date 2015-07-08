package examples.threadlocal;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Ignore;
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
    public void partialMockThreadLocalGetModule(@Mocked("getModule") final MyThreadLocal unused) {
        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations() {{
            MyThreadLocal.getThreadLocalCtx().getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicallyMockThreadLocalGetModule_instanceMocking() {
        final MyThreadLocal myThreadLocal = MyThreadLocal.getThreadLocalCtx();

        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations(myThreadLocal) {{
            myThreadLocal.getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicallyMockThreadLocalGetModule_classMocking() {
        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations(MyThreadLocal.class) {{
            MyThreadLocal.getThreadLocalCtx().getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicallyMockThreadLocalGetModule_classMockingWithLocalVal() {
        final MyThreadLocal myThreadLocal = MyThreadLocal.getThreadLocalCtx();

        final String expectedModule = "Mock Module";
        //noinspection UnusedDeclaration
        new Expectations(MyThreadLocal.class) {{
            myThreadLocal.getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Test
    public void partialMockedThreadLocal_mockedOtherService(
            @Mocked("getOtherService") final MyThreadLocal unused,
            @Mocked final OtherService otherService)
    {
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

    /**
     * This is the most sane pattern. But you need to be careful that you don't corrupt the thread for other tests.
     */
    @Test
    public void noThreadLocalMock_mockedOtherService(@Mocked final OtherService otherService) {
        final String expectedOtherServiceName = "Mock Other Service";
        MyThreadLocal.getThreadLocalCtx().init(otherService);

        //noinspection UnusedDeclaration
        new Expectations() {{
            otherService.getName();
            result = expectedOtherServiceName;
        }};

        MyService myService = new MyService();

        assertThat(myService.getOtherServiceName(), is(expectedOtherServiceName));
    }
}
