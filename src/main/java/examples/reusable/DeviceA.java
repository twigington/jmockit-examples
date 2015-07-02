package examples.reusable;

/**
 *
 */
public class DeviceA extends Device {
    private boolean active = false;

    public DeviceA(final String aName, final boolean on) {
        super(aName);
        active = on;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
