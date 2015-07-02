package examples.cascading;

/**
 *
 */
public class Word {
    private Paragraph paragraph;

    public Word(Paragraph paragraph) {
        this.paragraph = paragraph;
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public String useSingletonParagraph() {
        return Paragraph.getSingleton().getPage().getDocument().getName();
    }
}
