package examples.singleton;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 *
 */
public class ClassLoaderSingletonTest {
    @Test
    public void notMocked() throws Exception {
        final int result = ClassLoaderSingleton.getInstance().getOrElse("one", 2);

        assertThat(result, is(1));
    }


    @Test
    public void mocked(@Mocked final ClassLoaderSingleton mock) throws Exception {
        new Expectations() {{
            mock.getOrElse("one", 2);
            result = 2;
        }};

        final int result = ClassLoaderSingleton.getInstance().getOrElse("one", 2);

        assertThat(result, is(2));
    }
}