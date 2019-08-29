package ro.msg.edu.jbugs.entity.types;

public enum StatusType {
    OPEN("OPEN"),
    CLOSED("CLOSED"),
    IN_PROGRESS("IN_PROGRESS"),
    INFO_NEEDED("INFO_NEEDED"),
    REJECTED("REJECTED"),
    FIXED("FIXED");

    private String label;

    // enum constructor - cannot be public or protected
    StatusType(String label)
    {
        this.label = label;
    }
}
