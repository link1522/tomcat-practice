package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtils {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static DataSource dataSource = null;
    private static InputStream dbPropsIs = null;

    static {
        try {
            dbPropsIs = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            // dbPropsIs =
            // JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties");

            Properties dbProps = new Properties();
            dbProps.load(dbPropsIs);

            dataSource = DruidDataSourceFactory.createDataSource(dbProps);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbPropsIs != null) {
                    dbPropsIs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection createConn() {
        try {
            Connection conn = dataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Connection getConn() {
        Connection conn = threadLocal.get();

        if (conn == null) {
            conn = createConn();
            threadLocal.set(conn);
        }

        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();

        if (conn == null) {
            return;
        }

        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }
}
