package examples.inheritance;

import com.google.common.collect.Multiset;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FruitBasketTest {
    /**
     * In this test we see that Banana gets mocked also because it inherits from Produce.
     *
     */
    @Test
    public void mockAppleAndProduceFactory(@Mocked final Apple mockApple, @Mocked("factory") final Produce mockProduce) throws Exception {
        final Banana testBanana = new Banana("orange");

        new NonStrictExpectations() {{
            mockApple.getColor();
            result = "red"; // only produce red apples

            mockApple.isWormy();
            result = false;

            Produce.factory(Produce.Type.APPLE, anyString);
            result = mockApple;

            Produce.factory(Produce.Type.BANANA, anyString);
            result = testBanana;
        }};

        FruitBasket basket = new FruitBasket();
        basket.fillBasket();
        Multiset<String> colors = basket.getColorCounts();
        assertThat("red", colors.count("red"), is(3));
        assertThat("green", colors.count("green"), is(0));
        assertThat("yellow", colors.count("yellow"), is(0));
        assertThat("orange", colors.count("orange"), is(0));

        Banana myBanana = new Banana("green");
        assertThat(myBanana.getColor(), is("red"));

    }

    @Test
    public void usingInjectableOnApple(@Injectable final Apple mockApple, @Mocked final Produce mockProduce) throws Exception {
        final Banana testBanana = new Banana("orange");

        new NonStrictExpectations() {{
            mockApple.getColor();
            result = "red"; // only produce red apples

            mockApple.isWormy();
            result = false;

            Produce.factory(Produce.Type.APPLE, anyString);
            result = mockApple;

            Produce.factory(Produce.Type.BANANA, anyString);
            result = testBanana;
        }};

        FruitBasket basket = new FruitBasket();
        basket.fillBasket();
        Multiset<String> colors = basket.getColorCounts();
        assertThat("red", colors.count("red"), is(2));
        assertThat("green", colors.count("green"), is(0));
        assertThat("yellow", colors.count("yellow"), is(0));
        assertThat("orange", colors.count("orange"), is(1));

        Banana myBanana = new Banana("green");
        assertThat(myBanana.getColor(), is("green"));

    }
}