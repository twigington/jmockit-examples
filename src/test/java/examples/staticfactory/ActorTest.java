package examples.staticfactory;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ActorTest {
    @Ignore("No longer supported.")
    public void mockInException() throws Exception {
        new Expectations(){
            @Mocked MyService mock;
            {
                MyService.construct(anyString);
                result = mock;

                mock.doIt(anyInt);
                result = "mock1";
            }
        };

        final Actor anActor = new Actor();
        assertThat(anActor.doAction(8), is("mock1"));
    }

    @Test
    public void mockInTestMethodSig(@Mocked final MyService mock) throws Exception {
        new Expectations(){{
            MyService.construct(anyString);
            result = mock;

            mock.doIt(anyInt);
            result = "mock2";
        }};

        final Actor anActor = new Actor();
        assertThat(anActor.doAction(8), is("mock2"));
    }
}
