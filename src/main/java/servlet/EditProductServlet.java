package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import DAO.ProductDAOImpl;
import entity.Product;
import utils.StringUtils;

@WebServlet("/editProduct")
public class EditProductServlet extends ViewBaseServlet {
  private ProductDAO productDao = new ProductDAOImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter("id");

    if (StringUtils.isEmpty(id)) {
      resp.sendRedirect("product");
      return;
    }

    Product product = productDao.getById(Integer.parseInt(id));

    if (product == null) {
      resp.sendRedirect("product");
      return;
    }

    req.setAttribute("product", product);

    processTemplate("edit", req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String idStr = req.getParameter("id");
    int id = Integer.parseInt(idStr);
    String name = req.getParameter("name");
    String priceStr = req.getParameter("price");
    int price = Integer.parseInt(priceStr);
    String description = req.getParameter("description");

    Product product = productDao.getById(id);

    if (product == null) {
      resp.sendRedirect("product");
      return;
    }

    Product newProduct = new Product(id, name, price, description);
    productDao.update(newProduct);

    resp.sendRedirect("product");
  }
}
