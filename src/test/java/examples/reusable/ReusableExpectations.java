package examples.reusable;

import mockit.NonStrictExpectations;

/**
 *
 */
public final class ReusableExpectations extends NonStrictExpectations {
    public ReusableExpectations(DeviceHandler handler) {
        handler.doSomething(withInstanceOf(Device.class));
        result = 56;
    }
}
