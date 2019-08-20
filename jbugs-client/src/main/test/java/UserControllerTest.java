import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ro.msg.edu.jbugs.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserControllerTest {

    @Test
    public void getUsers() throws JsonProcessingException {
        List<UserDTO> testList = new ArrayList<>();

        UserDTO testUser1 = new UserDTO(0, "test", "test", "077", "email",
                "username", "passw", 0);

        UserDTO testUser2 = new UserDTO(0, "test2", "test2", "077", "email",
                "username", "passw", 0);

        testList.add(testUser1);
        testList.add(testUser2);

        assertEquals(testList.size(), 2);

        ObjectMapper obj = new ObjectMapper();
        String json = obj.writeValueAsString(testList);

        System.out.println(json);

    }
}