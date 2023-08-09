package controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import entity.Product;
import servlet.ViewBaseServlet;
import utils.StringUtils;

@SuppressWarnings("unused")
public class ProductController {
    private ProductDAO productDAO = new ProductDAOImpl();
    private final int perPage = 5;

    private String index(HttpServletRequest req) {
        String pageStr = req.getParameter("page");
        String keyword = req.getParameter("keyword");
        int totalPage;

        if (StringUtils.isEmpty(pageStr)) {
            pageStr = "1";
        }

        int page = Integer.parseInt(pageStr);

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
        req.setAttribute("productList", productList);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("keyword", keyword);

        return "product";
    }

    private String create(HttpServletRequest req) {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        productDAO.create(new Product(0, name, price, description));

        return "redirect:product.do";
    }

    private String edit(HttpServletRequest req) {
        String id = req.getParameter("id");

        if (StringUtils.isEmpty(id)) {
            return "redirect:product.do";
        }

        Product product = productDAO.getById(Integer.parseInt(id));

        if (product == null) {
            return "redirect:product.do";
        }

        req.setAttribute("product", product);

        return "edit";
    }

    private String update(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        Product product = productDAO.getById(id);

        if (product == null) {
            return "redirect:product.do";
        }

        Product newProduct = new Product(id, name, price, description);
        productDAO.update(newProduct);

        return "redirect:product.do";
    }

    private String delete(HttpServletRequest req) {
        String id = req.getParameter("id");

        productDAO.deleteById(Integer.parseInt(id));

        return "redirect:product.do";
    }
}
