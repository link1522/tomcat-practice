package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ViewBaseServlet extends HttpServlet {
  private TemplateEngine templateEngine;

  @Override
  public void init() throws ServletException {
    ServletContext servletContext = this.getServletContext();

    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCacheTTLMs(Long.valueOf(60000L));
    templateResolver.setCacheable(true);
    templateResolver.setCharacterEncoding("UTF-8");

    this.templateEngine = new TemplateEngine();
    this.templateEngine.setTemplateResolver(templateResolver);
  }

  public void processTemplate(String view, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/html;charset=UTF-8");

    WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());

    templateEngine.process(view, ctx, response.getWriter());
  }
}
