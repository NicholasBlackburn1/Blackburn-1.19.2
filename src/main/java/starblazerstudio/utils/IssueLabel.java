package starblazerstudio.utils;


public enum IssueLabel {
    FEATURE("feature"),
    BUG("bug"),
    DOCUMENTATION("documentation"),
    QUESTION("question"),
    HELP_WANTED("help wanted"),
    GOOD_FIRST_ISSUE("good first issue"),
    ENHANCEMENT("enhancement"),
    INVALID("invalid"),
    WONTFIX("wontfix"),
    DUPLICATE("duplicate"),
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
