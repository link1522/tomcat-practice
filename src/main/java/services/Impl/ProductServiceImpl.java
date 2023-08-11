package services.impl;

import java.util.List;

import dao.ProductDAO;
import entity.Product;
import services.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO = null;
    final private int perPage = 5;

    @Override
    public boolean addProduct(Product product) {
        int affectRow = productDAO.create(product);
        return affectRow > 0;
    }

    @Override
    public List<Product> getProductList(Integer page, String keyword) {
        return productDAO.getPageList(perPage, page, keyword);
    }

    @Override
    public Product getProductById(Integer id) {
        return productDAO.getById(id);
    }

    @Override
    public boolean updateProduct(Product product) {
        int affectRow = productDAO.update(product);
        return affectRow > 0;
    }

    @Override
    public boolean deleteProduct(Integer id) {
        int affectRow = productDAO.deleteById(id);
        return affectRow > 0;
    }

    @Override
    public int getProductPage(String keyword) {
        long totalCount = productDAO.totalCount(keyword);
        return (int) (Math.ceil((double) totalCount / perPage));
    }

}
