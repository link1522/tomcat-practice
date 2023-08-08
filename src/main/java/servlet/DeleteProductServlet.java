package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends ViewBaseServlet {
  private ProductDAO productDao = new ProductDAOImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter("id");

    productDao.deleteById(Integer.parseInt(id));

    resp.sendRedirect("product");
  }
}
