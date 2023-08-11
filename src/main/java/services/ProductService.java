package services;

import java.util.List;

import entity.Product;

public interface ProductService {
    boolean addProduct(Product product);

    List<Product> getProductList(Integer page, String keyword);

    Product getProductById(Integer id);

    boolean updateProduct(Product product);

    boolean deleteProduct(Integer id);

    int getProductPage(String keyword);
}
