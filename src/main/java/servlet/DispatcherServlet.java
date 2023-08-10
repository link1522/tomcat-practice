package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.StringUtils;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String, Object> beanMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            InputStream appContextIs = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(appContextIs);

            NodeList beanNodeList = document.getElementsByTagName("bean");

            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);

                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");

                    Constructor<?> beanConstructor = Class.forName(className).getDeclaredConstructor();
                    Object beanObj = beanConstructor.newInstance();

                    beanMap.put(beanId, beanObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String servletPath = req.getServletPath();
        int dotDoIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(1, dotDoIndex);

        Object controllerBeanObj = beanMap.get(servletPath);

        String operate = req.getParameter("operate");

        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method declaredMethod = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class);

            if (declaredMethod != null) {
                Object returnObj = declaredMethod.invoke(controllerBeanObj, req);
                if (returnObj instanceof String returnStr) {
                    if (returnStr.startsWith("redirect:")) {
                        String redirectStr = returnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else {
                        processTemplate(returnStr, req, resp);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
