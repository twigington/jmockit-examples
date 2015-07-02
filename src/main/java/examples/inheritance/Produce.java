package examples.inheritance;

public class Produce {
    public enum Type {APPLE, BANANA}

    public static Produce factory(Type type, String color) {
        switch (type) {
            case APPLE:
                return new Apple(color);
            case BANANA:
                return new Banana(color);
            default:
                return null;
        }
    }
}
