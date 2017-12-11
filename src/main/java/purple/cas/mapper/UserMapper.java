package purple.cas.mapper;

import purple.cas.model.*;
import java.util.List;

public interface UserMapper {

    List<User> getAll();

    void insert(User user);

}
