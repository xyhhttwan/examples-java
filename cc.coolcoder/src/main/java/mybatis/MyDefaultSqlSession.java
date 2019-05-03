package mybatis;

import mybatis.executor.MyBaseExecutor;
import mybatis.executor.MyExecutor;
import mybatis.proxy.MyMapperProxy;

import java.lang.reflect.Proxy;

public class MyDefaultSqlSession implements MySqlSession{

    private MyExecutor myExecutor = new MyBaseExecutor();

    @Override
    public <T> T selectOne(String sql) {
        return  myExecutor.query(sql);
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(this));
    }
}
