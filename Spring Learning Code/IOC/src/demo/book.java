package demo;

public class book {
    private String bName;
    private String bAuthor;

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public book(String bName, String bAuthor) {
        this.bName = bName;
        this.bAuthor = bAuthor;
    }

    @Override
    public String toString() {
        return "book{" +
                "bName='" + bName + '\'' +
                ", bAuthor='" + bAuthor + '\'' +
                '}';
    }
}
