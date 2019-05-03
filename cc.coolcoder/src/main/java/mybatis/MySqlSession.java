package mybatis;

public interface MySqlSession {


    /**
     * 一个查询方法
     *
     * @param statement
     * @param <T>
     * @return T
     */
    <T> T selectOne(String statement);


    /**
     * 一个查询方法
     *
     * @param clazz
     * @param <T>
     * @return T
     */
    <T> T getMapper(Class<T> clazz);


}
