package FlatsManage;

import java.sql.*;

public class JDBCConnectDB {

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    public JDBCConnectDB() {
        try {
            // 1. 注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //装载一个类并且对其进行实例化的操作。
            //装载过程中使用到的类加载器是当前类。
            // 2. 获取连接对象
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/b1dx0", "root", "root");
            // 4. 获取语句对象
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet cQue(String sql) {
        try {
            // 5. 执行sql语句,获取结果集
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 6. 返回结果集
        return rs;
    }

    public int cUpd(String sql) {
        // 返回值为int >=0
        try {
            return stmt.executeUpdate(sql); // 5. 执行sql语句
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void c_close() {
        try {
            // 7. 关闭资源
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
//参考:JDBC删除和删除
// https://blog.csdn.net/qq_40611605/article/details/88749293