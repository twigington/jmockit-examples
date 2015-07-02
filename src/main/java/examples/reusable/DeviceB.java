package examples.reusable;

/**
 *
 */
public class DeviceB extends Device {
    private int uses = 0;

    public DeviceB(final String aName) {
        super(aName);
    }

    public void use() {
        uses++;
    }

    public int getUses() {
        return uses;
    }
}
