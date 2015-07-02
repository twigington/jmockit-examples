package examples.staticfactory;

public class MyService {
    private String name = "unnamed";

    private MyService(String aName) {
        this.name = aName;
    }

    public static MyService construct(String aName) {
        return new MyService(aName);
    }

    public String getName() {
        return name;
    }

    public String doIt(int aNumber) {
        return this.name + aNumber;
    }
}
