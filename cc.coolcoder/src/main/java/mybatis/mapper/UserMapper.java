package mybatis.mapper;

import mybatis.entity.User;

public interface UserMapper {

    User findByUserId(int id);

    int insertUser(User user);
}
