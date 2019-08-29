package ro.msg.edu.jbugs.entity.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum mapping the possible role types
 *
 * @author Mara Corina
 * @since 19.1.2
 */
public enum RoleType {
    ADMINISTRATOR("ADMINISTRATOR"),
    PROJECT_MANAGER("PROJECT_MANAGER"),
    TEST_MANAGER("TEST_MANAGER"),
    DEVELOPER("DEVELOPER"),
    TESTER("TESTER");

    private String actualString;

    RoleType(String actualString) {
        this.actualString = actualString;
    }

    public String getActualString() {
        return actualString;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, RoleType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (RoleType roleType : RoleType.values()) {
            lookup.put(roleType.getActualString(), roleType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static RoleType get(String actualString) {
        return lookup.get(actualString);
    }
}
