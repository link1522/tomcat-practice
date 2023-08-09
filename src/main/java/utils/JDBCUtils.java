package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtils {
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

  public static Connection getConnection() throws Exception {
    Connection conn = dataSource.getConnection();
    return conn;
  }
}
