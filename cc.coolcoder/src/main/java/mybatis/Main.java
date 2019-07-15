package mybatis;

import mybatis.entity.User;
import mybatis.mapper.UserMapper;

public class Main {


    public static void main(String[] args) {
        MySqlSession sqlSession = new MyDefaultSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findByUserId(1);

        userMapper.insertUser(null);
        System.out.println("user:" + user);
    }
}
