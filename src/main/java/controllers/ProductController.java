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
public class ProductController extends ViewBaseServlet {
    private ProductDAO productDAO = new ProductDAOImpl();
    private final int perPage = 5;

    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            pageRedirect(resp, totalPage, keyword);
            return;
        } else if (page < 1) {
            pageRedirect(resp, 1, keyword);
            return;
        }

        List<Product> productList = productDAO.getPageList(5, page, keyword);
        req.setAttribute("productList", productList);
        req.setAttribute("page", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("keyword", keyword);

        processTemplate("product", req, resp);
    }

    private void pageRedirect(HttpServletResponse resp, int page, String keyword) throws IOException {
        if (StringUtils.isNotEmpty(keyword)) {
            resp.sendRedirect("product.do?page=" + page + "&keyword=" + keyword);
        } else {
            resp.sendRedirect("product.do?page=" + page);
        }

        return;
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        productDAO.create(new Product(0, name, price, description));

        resp.sendRedirect("product.do");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (StringUtils.isEmpty(id)) {
            resp.sendRedirect("product.do");
            return;
        }

        Product product = productDAO.getById(Integer.parseInt(id));

        if (product == null) {
            resp.sendRedirect("product.do");
            return;
        }

        req.setAttribute("product", product);

        processTemplate("edit", req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        Product product = productDAO.getById(id);

        if (product == null) {
            resp.sendRedirect("product.do");
            return;
        }

        Product newProduct = new Product(id, name, price, description);
        productDAO.update(newProduct);

        resp.sendRedirect("product.do");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        productDAO.deleteById(Integer.parseInt(id));

        resp.sendRedirect("product.do");
    }
}
