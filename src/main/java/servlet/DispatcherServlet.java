package servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.DispatchServletException;
import io.BeanFactory;
import io.ClassPathXmlApplicationContext;
import utils.StringUtils;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;

    @Override
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        int dotDoIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(1, dotDoIndex);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = request.getParameter("operate");

        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method[] controllerMethods = controllerBeanObj.getClass().getDeclaredMethods();

            for (Method method : controllerMethods) {
                if (operate.equals(method.getName())) {
                    Parameter[] params = method.getParameters();
                    Object[] paramValues = new Object[params.length];

                    for (int i = 0; i < params.length; i++) {
                        String paramName = params[i].getName();

                        if ("request".equals(paramName)) {
                            paramValues[i] = request;
                        } else if ("response".equals(paramName)) {
                            paramValues[i] = response;
                        } else if ("session".equals(paramName)) {
                            paramValues[i] = request.getSession();
                        } else {
                            String paramTypeName = params[i].getType().getName();
                            String paramStr = request.getParameter(paramName);

                            if (paramStr == null) {
                                paramValues[i] = paramStr;
                            } else if ("java.lang.Integer".equals(paramTypeName)) {
                                paramValues[i] = Integer.parseInt(paramStr);
                            } else {
                                paramValues[i] = paramStr;
                            }

                        }
                    }

                    Object returnObj = method.invoke(controllerBeanObj, paramValues);

                    if (returnObj instanceof String returnStr) {
                        if (returnStr.startsWith("redirect:")) {
                            String redirectStr = returnStr.substring("redirect:".length());
                            response.sendRedirect(redirectStr);
                        } else {
                            processTemplate(returnStr, request, response);
                        }
                    }

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatchServletException("DispatchServlet error");
        }
    }
}
