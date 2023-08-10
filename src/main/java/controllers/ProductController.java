package controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import entity.Product;
import servlet.ViewBaseServlet;
import utils.StringUtils;

@SuppressWarnings("unused")
public class ProductController {
    private ProductDAO productDAO = new ProductDAOImpl();
    private final int perPage = 5;

    public String index(HttpServletRequest request, Integer page, String keyword) {
        int totalPage;

        if (page == null) {
            page = 1;
        }

        long totalCount;
        if (StringUtils.isEmpty(keyword)) {
            totalCount = productDAO.totalCount();
        } else {
            totalCount = productDAO.totalCount(keyword);
        }
        totalPage = (int) (Math.ceil((double) totalCount / perPage));

        if (page > totalPage) {
            return "redirect:product.do?page=" + totalPage + "&keyword=" + keyword;
        } else if (page < 1) {
            return "redirect:product.do?page=1&keyword=" + keyword;
        }

        List<Product> productList = productDAO.getPageList(5, page, keyword);
        request.setAttribute("productList", productList);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("keyword", keyword);

        return "product";
    }

    public String create(String name, Integer price, String description) {
        productDAO.create(new Product(0, name, price, description));

        return "redirect:product.do";
    }

    public String edit(HttpServletRequest request, Integer id) {
        Product product = productDAO.getById(id);

        if (product == null) {
            return "redirect:product.do";
        }

        request.setAttribute("product", product);

        return "edit";
    }

    public String update(Integer id, String name, Integer price, String description) {
        Product product = productDAO.getById(id);

        if (product == null) {
            return "redirect:product.do";
        }

        Product newProduct = new Product(id, name, price, description);
        productDAO.update(newProduct);

        return "redirect:product.do";
    }

    public String delete(Integer id) {
        productDAO.deleteById(id);

        return "redirect:product.do";
    }
}
