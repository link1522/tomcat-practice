package DAO;

import java.util.List;

import entity.Product;

public class ProductDAOImpl extends BaseDAO<Product> implements ProductDAO {
  @Override
  public int create(Product product) {
    String sql = "insert into products (name, price, description) value (?, ?, ?)";
    int affectRow = execute(sql, product.getName(), product.getPrice(), product.getDescription());

    return affectRow;
  }

  @Override
  public List<Product> getAll() {
    String sql = "select * from products";
    List<Product> list = queryMany(sql);

    return list;
  }

  @Override
  public List<Product> getPageList(int perPage, int page) {
    String sql = "select * from products limit ?, ?";
    int skip = (page - 1) * perPage;
    List<Product> list = queryMany(sql, skip, perPage);

    return list;
  }

  @Override
  public Product getById(int id) {
    String sql = "select * from products where id = ?";
    Product product = queryOne(sql, id);

    return product;
  }

  @Override
  public int update(Product product) {
    String sql = "update products set name = ?, price = ?, description = ? where id = ?";
    int affectRow = execute(sql, product.getName(), product.getPrice(), product.getDescription(),
        product.getId());

    return affectRow;
  }

  @Override
  public int deleteById(int id) {
    String sql = "delete from products where id = ?";
    int affectRow = execute(sql, id);

    return affectRow;
  }

  @Override
  public long totalCount() {
    String sql = "select count(*) from products";
    long count = queryOther(sql);

    return count;
  }

}
