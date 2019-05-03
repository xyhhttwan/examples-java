package mybatis.executor;

import java.sql.SQLException;

/**
 * 自定义执行器
 */
public interface MyExecutor {

    /**
     * 一个查询方法
     *
     * @param statement
     * @param <T>
     * @return
     */
    <T> T query(String statement);

    /**
     * 一个更新操作
     *
     * @param statement
     * @param <T>
     * @return
     */
    <T> T update(String statement);


}
