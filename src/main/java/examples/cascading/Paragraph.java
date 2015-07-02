package examples.cascading;

/**
 *
 */
public class Paragraph {
    private static Paragraph singleton;

    private Page page;

    public Paragraph(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public static Paragraph getSingleton() {
        if (singleton == null) {
            singleton = new Paragraph(new Page(new Document()));
        }
        return singleton;
    }
}
