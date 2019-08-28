package rest_app_config.type.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MethodPathType {
    private String method;
    private String path;
}
