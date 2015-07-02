package examples.staticfactory;

import mockit.Mock;
import mockit.MockUp;

public final class MyServiceMocks extends MockUp<MyService> {
    @Mock
    private void $init(String aName) {
        // do nothing
    }

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
}
