package examples.reusable;

import mockit.NonStrictExpectations;

/**
 *
 */
public class NonFinalReusableExpectations extends NonStrictExpectations {
    public NonFinalReusableExpectations(DeviceHandler handler) {
        handler.doSomething(withInstanceOf(Device.class));
        result = 57;
    }
}
