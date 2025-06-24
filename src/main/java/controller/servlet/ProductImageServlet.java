package controller.servlet;

import dao.ProductImageDAO;
import model.ProductImage;
import util.FileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "ProductController", value = "/product")
public class ProductImageServlet extends HttpServlet {
    private ProductImageDAO imageDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo FileService và ProductImageDAO
        FileService fileService = new FileService();
        this.imageDAO = new ProductImageDAO(fileService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        if (productId == null || productId.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
            return;
        }

        try {
            UUID productUUID = UUID.fromString(productId);
            List<ProductImage> images = imageDAO.getImagesByProductId(productUUID);
            request.setAttribute("images", images);
            request.setAttribute("contextPath", request.getContextPath());
            request.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Failed to load product images", e);
        }
    }
}