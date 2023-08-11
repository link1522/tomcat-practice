package controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import DAO.ProductDAO;
import DAO.Impl.ProductDAOImpl;
import entity.Product;
import services.ProductService;
import services.Impl.ProductServiceImpl;
import servlet.ViewBaseServlet;
import utils.StringUtils;

@SuppressWarnings("unused")
public class ProductController {
    private ProductService productService = new ProductServiceImpl();

    public String index(HttpServletRequest request, Integer page, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        }

        int totalPage = productService.getProductPage(keyword);

        if (page == null || page < 1) {
            page = 1;
        } else if (page > totalPage) {
            page = totalPage;
        }

        List<Product> productList = productService.getProductList(page, keyword);
        request.setAttribute("productList", productList);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("keyword", keyword);

        return "product";
    }

    public String create(String name, Integer price, String description) {
        productService.addProduct(new Product(0, name, price, description));

        return "redirect:product.do";
    }

    public String edit(HttpServletRequest request, Integer id) {
        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:product.do";
        }

        request.setAttribute("product", product);

        return "edit";
    }

    public String update(Integer id, String name, Integer price, String description) {
        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:product.do";
        }

        Product newProduct = new Product(id, name, price, description);
        productService.updateProduct(newProduct);

        return "redirect:product.do";
    }

    public String delete(Integer id) {
        productService.deleteProduct(id);

        return "redirect:product.do";
    }
}
