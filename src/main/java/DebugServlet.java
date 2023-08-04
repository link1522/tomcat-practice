import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DebugServlet extends HttpServlet {
  @Override
  public void init() throws ServletException {
    System.out.println("init...");
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("service...");
  }

  @Override
  public void destroy() {
    System.out.println("destory...");
  }
}
