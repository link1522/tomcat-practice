package DAO;

import java.util.List;

import entity.Product;

public interface ProductDAO {
  int create(Product product);

  List<Product> getAll();
}
