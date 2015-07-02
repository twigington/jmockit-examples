package examples.inheritance;

public class Fruit extends Produce {
    private String color;

    public Fruit(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
