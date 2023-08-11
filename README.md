# Tomcat mvc demo

## 演進過程

- 最初一個請求對應一個 Servlet
  - 此時會需要多個 Servlet，ex: CreateProductServlet、UpdateProductServlet ... 等
- 將所有分散的 Servlet 整合到一個 Servlet (ProductServlet)，再透過請求帶上 operate 參數來決定調用哪個方法
  - operate 透過 switch case 或反射來調用內部方法
- 將方法都放到 Controller (ProductController) 做到解偶，所有請求都透過 DispatcherServlet 來分派對應 Controller 上的方法
  - 反射的代碼都提取到 DispatcherServlet
  - 參數的獲取也都提取到 DispatcherServlet
- 分離出 service 來對應舉體業務邏輯，而不是直接調用 DAO
- 解耦類的依賴關係，建立 ClassPathXmlApplicationContext 類，內部讀取 applicationContext.xml 創建需要使用的實例(ex: ProductDAO、ProductService 等)，並透過反射加入所需要的依賴，存於 beanMap 屬性中
  - 此時便不需要自行在類中 new 實例 (解耦)
