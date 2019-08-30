package rest_app_config.type.util;

public class RequestType{
    private MethodPathType methodPathObject;

    public RequestType(String method, String path) {
        /*
         * trim path for digits, update path with suffix "{id}"
         */
        String pathWithoutDigits = path.replaceAll("\\d",""); // ("[0-9]","");
        if(!path.equals(pathWithoutDigits)){
            pathWithoutDigits = pathWithoutDigits + "{id}";
        }
        this.methodPathObject = new MethodPathType(method, pathWithoutDigits);
    }
    // to be modified...........................
    public boolean matches(RegisteredRequestType registeredRequestType) {
        if(registeredRequestType == RegisteredRequestType.OPTIONS){
            if(RegisteredRequestType.OPTIONS.getMethodPathObject().getMethod()
                    .equals(this.methodPathObject.getMethod())){
                return true;
            }
        }
        if(registeredRequestType == RegisteredRequestType.GET_NOTIFICATIONS){
            if(RegisteredRequestType.GET_NOTIFICATIONS.getMethodPathObject().getMethod()
                    .equals(this.methodPathObject.getMethod())
                    && this.methodPathObject.getPath().endsWith(RegisteredRequestType.GET_NOTIFICATIONS.getMethodPathObject().getPath())){
                return true;
            }
        }
        if(registeredRequestType.getMethodPathObject().getMethod().equals(this.methodPathObject.getMethod())
                && registeredRequestType.getMethodPathObject().getPath().equals(this.methodPathObject.getPath())){
            return true;
        }
        return false;
    }
}
