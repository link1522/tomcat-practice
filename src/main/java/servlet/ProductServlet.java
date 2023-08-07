package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import entity.Product;

@WebServlet("/product")
public class ProductServlet extends ViewBaseServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProductDAO productDAO = new ProductDAOImpl();

    List<Product> productList = productDAO.getAll();
    req.setAttribute("productList", productList);

    processTemplate("product", req, resp);
  }
}
