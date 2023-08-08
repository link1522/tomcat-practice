package DAO;

import java.util.List;

import entity.Product;

public interface ProductDAO {
  int create(Product product);

  List<Product> getAll();

  List<Product> getPageList(int perPage, int page);

  Product getById(int id);

  long totalCount();

  int update(Product product);

  int deleteById(int id);
}
