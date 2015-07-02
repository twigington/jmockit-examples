package examples.cascading;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * This test shows how you can using the Cascading annotation to reduce the mocks you need to define.
 */
public class WordTest {
    /**
     * You can cascade mock and get mock instances of owned objects
     */
    @Test
    public void cascadeTest(@Mocked final Paragraph mockParagraph) throws Exception {
        final Word myWord = new Word(mockParagraph);
        assertThat(myWord.getParagraph().getPage().getDocument(), is(notNullValue()));
    }

    /**
     * You can cascade mock down to a method and specify the results.
     */
    @Test
    public void cascadeTestWithMethodCall(@Mocked final Paragraph mockParagraph) throws Exception {
        final String expectedName = "Mock Document!";

        new Expectations() {{
            mockParagraph.getPage().getDocument().getName();
            result = expectedName;
        }};

        final Word myWord = new Word(mockParagraph);
        assertThat(myWord.getParagraph().getPage().getDocument().getName(), is(expectedName));
    }

    /**
     * You can even use Cascading when starting from a static singleton method.
     */
    @Test
    public void cascadeTestWithStaticCall(@Mocked final Paragraph mockParagraph) throws Exception {
        final String expectedName = "Mock Document!";

        new Expectations() {{
            Paragraph.getSingleton().getPage().getDocument().getName();
            result = expectedName;
        }};

        final Word myWord = new Word(mockParagraph);
        assertThat(myWord.useSingletonParagraph(), is(expectedName));
    }

    /**
     * This test is here to verify that when not mocked the answer is as expected.
     */
    @Test
    public void noMockingTest() throws Exception {
        Word myWord = new Word(new Paragraph(new Page(new Document())));
        assertThat(myWord.getParagraph().getPage().getDocument().getName(), is("Real Document"));

        assertThat(myWord.useSingletonParagraph(), is("Real Document"));
    }
}
