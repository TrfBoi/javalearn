package util;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCUtil {
    private static String url;
    private static String mysqlusr;
    private static String mysqlpassword;
    private Connection connection;
    private PreparedStatement preparedStatement;

    //静态代码块，用于配置信息与驱动（Driver）的加载
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");//配置文件常用的读取方法
        url = resourceBundle.getString("url");
        mysqlusr = resourceBundle.getString("mysqlusr");
        mysqlpassword = resourceBundle.getString("mysqlpassword");
        System.out.println("配置文件读取成功");
    }

    //java与数据库连接通道创建方法
    public void creatConnection(){
        try {
            connection = DriverManager.getConnection(url, mysqlusr, mysqlpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //数据操作对象的创建方法，由于调用了通道创建方法，所以通道连接在主程序中不用调用了
    public PreparedStatement creatPreparedStatement(String sql){
        creatConnection();
        try {
            preparedStatement = this.connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    //通道与数据访问对象的关闭，增删改三种操作调用它
    public void close(){
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //重载，并且调用上面的无参方法，关闭通道、数据访问对象与返回结果对象。查询操作的资源关闭调用此方法
    public void close(ResultSet resultSet){
        close();
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
