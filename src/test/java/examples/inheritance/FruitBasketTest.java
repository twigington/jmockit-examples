package examples.inheritance;

import com.google.common.collect.Multiset;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FruitBasketTest {
    /**
     * In this test we see that Banana gets mocked also because it inherits from Produce.
     *
     */
    @Test
    public void mockAppleAndProduceFactory(@Mocked final Apple mockApple) throws Exception {
        final Banana testBanana = new Banana("orange");

        new Expectations(Produce.class) {{
            Produce.factory(Produce.Type.APPLE, anyString);
            result = mockApple;

            Produce.factory(Produce.Type.BANANA, anyString);
            result = testBanana;
        }};

        new Expectations() {{
            mockApple.getColor();
            result = "red"; // only produce red apples
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

    /**
     * This test shows how Injectable can be used to to isolate a mock.
     */
    @Test
    public void usingInjectableOnApple(@Injectable final Apple mockApple) throws Exception {
        final Banana testBanana = new Banana("orange");

        new Expectations(Produce.class) {{
            Produce.factory(Produce.Type.APPLE, anyString);
            result = mockApple;

            Produce.factory(Produce.Type.BANANA, anyString);
            result = testBanana;
        }};

        new NonStrictExpectations() {{
            mockApple.getColor();
            result = "red"; // only produce red apples

            mockApple.isWormy();
            result = false;
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
