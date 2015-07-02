package examples.inheritance;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FruitBasket {
    private Collection<Fruit> items = new ArrayList<Fruit>();

    public boolean addFruit(Fruit aFruit) {
        return items.add(aFruit);
    }

    public List<Fruit> getFruitList() {
        return new ArrayList<Fruit>(items);
    }

    public Multiset<String> getColorCounts() {
        Multiset<String> fruitClassCnt = HashMultiset.create();
        for (Fruit aFruit: items) {
            fruitClassCnt.add(aFruit.getColor());
        }

        return fruitClassCnt;
    }

    public void fillBasket() {
        items.add((Fruit) Produce.factory(Produce.Type.APPLE, "green"));
        items.add((Fruit) Produce.factory(Produce.Type.APPLE, "red"));
        items.add((Fruit) Produce.factory(Produce.Type.BANANA, "yellow"));
    }
}
