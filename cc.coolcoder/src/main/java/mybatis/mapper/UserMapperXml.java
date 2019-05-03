package mybatis.mapper;

import java.util.HashMap;
import java.util.Map;

public class UserMapperXml {


    public static final String NAME_SPACE = "mybatis.mapper.UserMapper";

    private static Map<String, String> methodMap = new HashMap<>();

    static {
        //先不采用xml形式
        methodMap.put("findByUserId", "select id,name,pwd from user where id =#{id}");

        methodMap.put("insertUser", "insert into user(name,pwd) values(#{name},#{pwd})");

    }

    public static String getMethodSql(String statement) {
        return methodMap.get(statement);
    }
}
