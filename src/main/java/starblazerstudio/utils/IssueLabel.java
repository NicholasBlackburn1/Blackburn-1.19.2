package starblazerstudio.utils;


public enum IssueLabel {
    FEATURE("Feature"),
    BUG("BUG"),
    CRASH("crash");
    private final String label;

    IssueLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
