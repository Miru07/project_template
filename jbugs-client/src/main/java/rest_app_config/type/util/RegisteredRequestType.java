package rest_app_config.type.util;

import lombok.Getter;
import ro.msg.edu.jbugs.type.PermissionType;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum RegisteredRequestType {
    OPTIONS("OPTIONS", "", ""),
    LOGIN("POST", "/jbugs/api/login", ""),

    GET_USERS("GET", "/jbugs/api/users", PermissionType.USER_MANAGEMENT.getActualString()),
    CREATE_USER("POST", "/jbugs/api/users/create-user", PermissionType.USER_MANAGEMENT.getActualString()),
    IS_DEACTIVATABLE_ID("GET", "/jbugs/api/users/is-deactivatable/{id}", PermissionType.USER_MANAGEMENT.getActualString()),
    EDIT_USER("PUT", "/jbugs/api/users/edit-user", PermissionType.USER_MANAGEMENT.getActualString()),
    GET_USER_ID("PUT", "/jbugs/api/users/{id}", PermissionType.USER_MANAGEMENT.getActualString()),

    GET_BUGS("GET", "/jbugs/api/bugs", PermissionType.BUG_MANAGEMENT.getActualString()),
    CREATE_BUG("POST", "/jbugs/api/bugs", PermissionType.BUG_MANAGEMENT.getActualString()),
    UPDATE_BUG_ID("PUT", "/jbugs/api/bugs/update-bug-status/{id}", PermissionType.BUG_MANAGEMENT.getActualString()),
    UPDATE_BUG("PUT", "/jbugs/api/bugs/update-bug/{id}", PermissionType.BUG_MANAGEMENT.getActualString()), // Sebi? method in Bug Controller
    // CLOSE_BUG("PUT", "/jbugs/api/bugs/close"); // TODO Miruna? method in Bug Controller
    // GET_USERS_FOR_BUG_VIEW("PUT", "/jbugs/api/bugs/users??"); // TODO Miruna? method in Bug Controller
    // EXPORT_BUGS("", "/jbugs/api/bugs/"); // TODO Diana? method in Bug Controller
    // EXPORT_BUG("", "/jbugs/api/bugs/"); // TODO method in Bug Controller

    GET_PERMISSIONS("GET", "/jbugs/api/roles/get-permissions/{id}", PermissionType.PERMISSION_MANAGEMENT.getActualString()),
    SET_PERMISSIONS("PUT", "/jbugs/api/roles/set-permissions", PermissionType.PERMISSION_MANAGEMENT.getActualString());

    private MethodPathType methodPathObject;
    private String permission;

    RegisteredRequestType(String method, String pathWithoutDigits, String permissionType) {
        this.methodPathObject = new MethodPathType(method, pathWithoutDigits);
        this.permission = permissionType;
    }

    //****** Reverse Lookup Implementation************//

    //Lookup table
    private static final Map<MethodPathType, RegisteredRequestType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (RegisteredRequestType requestType : RegisteredRequestType.values()) {
            lookup.put(requestType.getMethodPathObject(), requestType);
        }
    }

    //This method can be used for reverse lookup purpose
    public static RegisteredRequestType get(MethodPathType methodPathObject) {
        return lookup.get(methodPathObject);
    }
}

