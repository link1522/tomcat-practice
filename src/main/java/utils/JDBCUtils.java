package utils;

import java.io.InputStream;
import java.sql.Connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.cj.jdbc.Driver;

public class JDBCUtils {
  private static DruidDataSource dataSource = null;
  private static InputStream dbPropsIs = null;

  static {
    try {
      // TODO load config by .properties file
      // dbPropsIs = ClassLoader.getSystemResourceAsStream("/WEB-INF/db.properties");
      // FileInputStream fis = new FileInputStream("./db.properties");

      // Properties dbProps = new Properties();
      // dbProps.load(dbPropsIs);

      // dataSource = DruidDataSourceFactory.createDataSource(dbProps);

      dataSource = new DruidDataSource();
      dataSource.setDriver(new Driver());
      dataSource.setUrl("jdbc:mysql:///test");
      dataSource.setUsername("root");
      dataSource.setPassword("root");
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
