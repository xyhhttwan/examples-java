package mybatis.executor;

import mybatis.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyBaseExecutor implements MyExecutor {

    /**
     * 数据库地址
     */
    private static String dbUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
    /**
     * 用户名
     */
    private static String dbUserName = "root";
    /**
     * 密码
     */
    private static String dbPassword = "pwd#123456";
    /**
     * 驱动名称
     */
    private static String jdbcName = "com.mysql.jdbc.Driver";

    @Override
    public <T> T query(String sql) {

        final List<Object> multipleResults = new ArrayList<Object>();

        Connection con = null;
        User user = null;
        try {
            con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPwd(resultSet.getString("pwd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != con) {
                try {
                    con.close();
                } catch (SQLException ignore) {

                }
            }
        }


        return (T) user;
    }

    @Override
    public <T> T update(String statement) {
        return null;
    }


    private Connection getConnection() {

        try {
            Class.forName(jdbcName);
            System.out.println("加载驱动成功！");
        } catch (ClassNotFoundException e) {

            System.out.println("加载驱动失败！");
            e.printStackTrace();
        }

        Connection con = null;
        try {
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            System.out.println("获取数据库连接成功！");
            System.out.println("进行数据库操作！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取数据库连接失败！");
        }
        return con;
    }

}
