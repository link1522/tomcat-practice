package DAO;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import entity.Product;
import utils.JDBCUtils;

public class ProductDAOImpl implements ProductDAO {

  @Override
  public int create(Product product) {
    Connection conn = null;

    try {
      conn = JDBCUtils.getConnection();
      QueryRunner queryRunner = new QueryRunner();

      String sql = "insert into products (name, price, description) value (?, ?, ?)";
      int affectRow = queryRunner.update(conn, sql, product.getName(), product.getPrice(), product.getDescription());
      return affectRow;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(conn, null, null);
    }

    return 0;
  }

  @Override
  public List<Product> getAll() {
    Connection conn = null;

    try {
      conn = JDBCUtils.getConnection();
      QueryRunner queryRunner = new QueryRunner();

      String sql = "select * from products";
      BeanListHandler<Product> handler = new BeanListHandler<>(Product.class);

      List<Product> list = queryRunner.query(conn, sql, handler);

      return list;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      DbUtils.closeQuietly(conn, null, null);
    }

    return null;
  }
}
