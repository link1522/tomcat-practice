package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import entity.Product;

@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    String priceStr = request.getParameter("price");
    int price = Integer.parseInt(priceStr);
    String description = request.getParameter("description");

    ProductDAO productDAO = new ProductDAOImpl();
    productDAO.create(new Product(0, name, price, description));

    response.sendRedirect("product");
  }
}
