
package ro.msg.edu.jbugs.entity.types;

import java.util.HashMap;
import java.util.Map;

public enum NotificationType {

    WELCOME_NEW_USER("WELCOME_NEW_USER"),
    USER_UPDATED("USER_UPDATED"),
    USER_DELETED("USER_DELETED"),
    BUG_UPDATED("BUG_UPDATED"),
    BUG_CLOSED("BUG_CLOSED"),
    BUG_STATUS_UPDATED("BUG_STATUS_UPDATED"),
    USER_DEACTIVATED("USER_DEACTIVATED");

    private String actualString;

    NotificationType(String actualString) {
        this.actualString = actualString;
    }

    public String getActualString() {
        return actualString;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<String, NotificationType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (NotificationType notificationType : NotificationType.values()) {
            lookup.put(notificationType.getActualString(), notificationType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static NotificationType get(String actualString) {
        return lookup.get(actualString);
    }

}
