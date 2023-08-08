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
import utils.StringUtils;

@WebServlet("/product")
public class ProductServlet extends ViewBaseServlet {
  private ProductDAO productDAO = new ProductDAOImpl();
  private final int perPage = 5;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String pageStr = req.getParameter("page");

    if (StringUtils.isEmpty(pageStr)) {
      pageStr = "1";
    }

    int page = Integer.parseInt(pageStr);
    int totalPage = (int) (Math.ceil((double) productDAO.totalCount() / perPage));

    if (page > totalPage) {
      pageRedirect(resp, totalPage);
      return;
    } else if (page < 1) {
      pageRedirect(resp, 1);
      return;
    }

    List<Product> productList = productDAO.getPageList(5, page);
    req.setAttribute("productList", productList);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);

    processTemplate("product", req, resp);
  }

  private void pageRedirect(HttpServletResponse resp, int page) throws IOException {
    resp.sendRedirect("product?page=" + page);
    return;
  }
}
