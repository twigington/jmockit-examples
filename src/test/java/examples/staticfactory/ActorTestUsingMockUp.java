package examples.staticfactory;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ActorTestUsingMockUp {
    @Ignore("Does not seem to be working any longer")
    @Test
    public void mockInTestMethod() throws Exception {
        new MockUp<MyService>() {
            @Mock
            public MyService construct(String aName) {
                return getMockInstance();
            }

            @Mock
            public String getName() {
                return "MockUp";
            }

            @Mock
            public String doIt(int aNumber) {
                return "MockUp" + 1;
            }
        };
        final Actor anActor = new Actor();
        assertThat(anActor.doAction(8), is("MockUp1"));
    }

    // Try it with a NonStrictExpectation.
    @Test
    public void mockWithNonStrictExpectation(@Mocked final MyService mockService) throws Exception {
        new NonStrictExpectations() {{
            MyService.construct(anyString);
            result = mockService;

            mockService.getName();
            result = "MockUpNSE";

            mockService.doIt(anyInt);
            result = "MockUpNSE9";
        }};

        final Actor anActor = new Actor();
        assertThat(anActor.doAction(8), is("MockUpNSE9"));
    }

    /**
     * Using a pre-defined MockUp doesn't appear to give us a way to mock the static "constructor" method and have
     * access to the mock instance.
     *
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void mockWithMockUp() throws Exception {
        new MyServiceMocks();

        final Actor anActor = new Actor();
        assertThat(anActor.doAction(8), is("MockUp2"));
    }
}
