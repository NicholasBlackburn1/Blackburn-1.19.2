package starblazerstudio.utils;


public enum IssueLabel {
    FEATURE("FEATURE"),
    BUG("BUG"),
    CRASH("CRASH");
    private final String label;

    IssueLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
