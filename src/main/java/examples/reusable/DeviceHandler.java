package examples.reusable;

/**
 *
 */
public class DeviceHandler {
    public int doSomething(final Device aDevice) {
        return aDevice.getName().length();
    }

    public <T extends Device> T action(final T object) throws Exception {
        return (T) object.getClass().newInstance();
    }
}
