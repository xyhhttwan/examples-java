package mybatis.proxy;

import mybatis.MySqlSession;
import mybatis.mapper.UserMapperXml;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author baixiaobin
 */
public class MyMapperProxy implements InvocationHandler {


    public MyMapperProxy() {
    }

    private MySqlSession mySqlSession;

    public MyMapperProxy(MySqlSession mySqlSession) {
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodClassName = method.getDeclaringClass().getName();
        if (methodClassName.equals(UserMapperXml.NAME_SPACE)) {
            String methodName = method.getName();
            String originSql = UserMapperXml.getMethodSql(methodName);

            String sql = originSql.replace("#{id}", String.valueOf(args[0]));
            return mySqlSession.selectOne(sql);
        }
        return null;
    }
}
