package starblazerstudio.utils;


public enum IssueLabel {
    FEATURE("Feature"),
    BUG("bug"),
    QUESTION("question"),
    MAINTENANCE("maintenance"),
    PERFORMANCE("performance");

    private final String label;

    IssueLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
