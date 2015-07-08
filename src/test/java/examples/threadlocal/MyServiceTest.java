package examples.threadlocal;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Various examples of dealing with a ThreadLocal Subclass.
 * <b/>
 * I would recommend against creating a class like {@link MyThreadLocal}. Favor localized
 * ThreadLocal variables as opposed to this "thread global" approach.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html">https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html</a>
 * @see <a href="https://plumbr.eu/blog/java/when-and-how-to-use-a-threadlocal">https://plumbr.eu/blog/java/when-and-how-to-use-a-threadlocal</a>
 * @see <a href="http://tutorials.jenkov.com/java-concurrency/threadlocal.html">http://tutorials.jenkov.com/java-concurrency/threadlocal.html</a>
 */
@SuppressWarnings("UnusedParameters")
public class MyServiceTest {
    @Test
    public void noMocking() {
        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is("Default Module"));
    }

    /**
     * This seems to be the most sane pattern.
     * You will need to be careful that you don't corrupt the thread local for other tests.
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

    /**
     * Will not work with version 1.19.
     */
    @Test
    public void partialMockedThreadLocal_mockedOtherService(
            @Mocked("getOtherService") final MyThreadLocal unused,
            @Mocked final OtherService otherService)
    {
        final String expectedOtherServiceName = "Mock Other Service";

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
     * Will not work with version 1.19.
     */
    @Test
    public void dynamicPartialThreadLocalMockGetModule_(@Mocked("getModule") final MyThreadLocal unused) {
        final String expectedModule = "Mock Module";
        new Expectations() {{
            MyThreadLocal.getThreadLocalCtx().getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicPartialThreadLocalMockGetModule_instanceMocking() {
        final MyThreadLocal myThreadLocal = MyThreadLocal.getThreadLocalCtx();

        final String expectedModule = "Mock Module";
        new Expectations(myThreadLocal) {{
            myThreadLocal.getModule();
            result = expectedModule;
        }};

        MyService myService = new MyService();

        assertThat(myService.getMyThreadLocalModule(), is(expectedModule));
    }

    @Ignore("jmockit ends up in a loop and a StackOverflow error results")
    @Test
    public void dynamicPartialThreadLocalMockGetModule_classMocking() {
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
    public void dynamicPartialThreadLocalMockGetModule_classMockingWithLocalVal() {
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
}
