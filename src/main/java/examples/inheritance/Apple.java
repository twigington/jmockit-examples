package examples.inheritance;

public class Apple extends Fruit {
    private boolean wormy = false;

    public Apple(String color) {
        super(color);
    }

    public boolean isWormy() {
        return wormy;
    }

    public void setWormy(boolean wormy) {
        this.wormy = wormy;
    }

    public boolean eat() {
        return !isWormy();
    }
}
