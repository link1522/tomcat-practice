package servlet;

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
import utils.StringUtils;

@WebServlet("/product")
public class ProductServlet extends ViewBaseServlet {
    private ProductDAO productDAO = new ProductDAOImpl();
    private final int perPage = 5;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String operate = req.getParameter("operate");

        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        Method[] declaredMethods = this.getClass().getDeclaredMethods();

        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName.equals(operate)) {
                try {
                    method.invoke(this, req, resp);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        index(req, resp);
    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");
        String keyword = req.getParameter("keyword");
        int totalPage;

        if (StringUtils.isEmpty(pageStr)) {
            pageStr = "1";
        }

        int page = Integer.parseInt(pageStr);

        if (StringUtils.isEmpty(keyword)) {
            totalPage = (int) (Math.ceil((double) productDAO.totalCount() / perPage));
        } else {
            totalPage = (int) (Math.ceil((double) productDAO.totalCount(keyword) / perPage));
        }

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
            resp.sendRedirect("product?page=" + page + "&keyword=" + keyword);
        } else {
            resp.sendRedirect("product?page=" + page);
        }

        return;
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        productDAO.create(new Product(0, name, price, description));

        resp.sendRedirect("product");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (StringUtils.isEmpty(id)) {
            resp.sendRedirect("product");
            return;
        }

        Product product = productDAO.getById(Integer.parseInt(id));

        if (product == null) {
            resp.sendRedirect("product");
            return;
        }

        req.setAttribute("product", product);

        processTemplate("edit", req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String description = req.getParameter("description");

        Product product = productDAO.getById(id);

        if (product == null) {
            resp.sendRedirect("product");
            return;
        }

        Product newProduct = new Product(id, name, price, description);
        productDAO.update(newProduct);

        resp.sendRedirect("product");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        productDAO.deleteById(Integer.parseInt(id));

        resp.sendRedirect("product");
    }
}
