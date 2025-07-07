package controller.servlet;

import dao.ProductDAO;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@WebServlet(name = "ProductDetailServlet", urlPatterns = {"/product-detail"})
public class ProductDetailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ProductDetailServlet.class.getName());
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("id");
        Product product = null;

        if (productIdParam != null && !productIdParam.trim().isEmpty()) {
            try {
                UUID productId = UUID.fromString(productIdParam);
                product = productDAO.getProductById(productId);
            } catch (IllegalArgumentException e) {
                LOGGER.warning("Invalid product ID format: " + productIdParam + " - " + e.getMessage());
                request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
            }
        } else {
            LOGGER.warning("Product ID is missing in the request.");
            request.setAttribute("errorMessage", "ID sản phẩm không được cung cấp.");
        }

        if (product != null) {
            request.setAttribute("product", product);
            request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy sản phẩm.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
