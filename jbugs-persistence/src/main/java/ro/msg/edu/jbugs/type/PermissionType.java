package ro.msg.edu.jbugs.type;

import java.util.HashMap;
import java.util.Map;

public enum PermissionType {
    PERMISSION_MANAGEMENT("PERMISSION_MANAGEMENT"),
    USER_MANAGEMENT("USER_MANAGEMENT"),
    BUG_MANAGEMENT("BUG_MANAGEMENT"),
    BUG_CLOSE("BUG_CLOSE"),
    BUG_EXPORT_PDF("BUG_EXPORT_PDF"),
    CURRENT_USER("CURRENT_USER");

    private String actualString;

    PermissionType(String actualString) {
        this.actualString = actualString;
    }

    public String getActualString() {
        return actualString;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, PermissionType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (PermissionType permissionType : PermissionType.values()) {
            lookup.put(permissionType.getActualString(), permissionType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static PermissionType get(String actualString) {
        return lookup.get(actualString);
    }
}
