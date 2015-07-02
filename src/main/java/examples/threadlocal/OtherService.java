package examples.threadlocal;

/**
 *
 */
public class OtherService {
    private String name = "OtherService";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int operate(int x, int y) {
        return x + y;
    }
}
