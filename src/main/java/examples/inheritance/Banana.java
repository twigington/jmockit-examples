package examples.inheritance;

public class Banana extends Fruit {
    public Banana(String color) {
        super(color);
    }

    public boolean peal() {
        // do peal operation only if the color isn't green
        return !"green".equalsIgnoreCase(getColor());
    }
}
