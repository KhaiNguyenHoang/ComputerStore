package controller.servlet;

import dao.ProductDAO;
import dao.ProductImageDAO;
import model.Product;
import model.ProductImage;
import util.FileService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/products", "/products/add", "/products/edit", "/products/delete", "/products/details"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 25)
public class ProductServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServlet.class);
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private ProductDAO productDAO;
    private ProductImageDAO productImageDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        FileService fileService = new FileService(); // Replace with actual FileService implementation
        productImageDAO = new ProductImageDAO(fileService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagePath = request.getServletPath();
        try {
            switch (pagePath) {
                case "/products/add":
                    showAddForm(request, response);
                    break;
                case "/products/edit":
                    showEditForm(request, response);
                    break;
                case "/products/details":
                    showProductDetails(request, response);
                    break;
                case "/products/delete":
                    deleteProduct(request, response);
                    break;
                case "/products":
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException e) {
            LOGGER.error("Database error in GET request for {}: {}", pagePath, e.getMessage(), e);
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Placeholder for CSRF token validation
        if (!validateCsrfToken(request)) {
            LOGGER.warn("CSRF validation failed for request: {}", request.getServletPath());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
            return;
        }

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/products/add":
                    addProduct(request, response);
                    break;
                case "/products/edit":
                    updateProduct(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/products");
                    break;
            }
        } catch (SQLException e) {
            LOGGER.error("Database error in POST request for {}: {}", action, e.getMessage(), e);
            request.setAttribute("error", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid input in POST request for {}: {}", action, e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/" + (action.contains("add") ? "addProduct.jsp" : "editProduct.jsp")).forward(request, response);
        } catch (Exception e) {
            LOGGER.error("Unexpected error in POST request for {}: {}", action, e.getMessage(), e);
            request.setAttribute("error", "An unexpected error occurred. Please try again later.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private boolean validateCsrfToken(HttpServletRequest request) {
        // Implement CSRF token validation logic here
        // Example: Check request.getParameter("csrfToken") against session-stored token
        return true; // Placeholder
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        int maxPages = 100; // Prevent excessive pagination

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
                if (page > maxPages) page = maxPages;
            } catch (NumberFormatException e) {
                page = 1;
                LOGGER.warn("Invalid page parameter: {}", pageParam);
            }
        }

        List<Product> products = productDAO.getProducts(page, pageSize);
        int totalProducts = productDAO.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Fetch main image for each product and set in images list
        for (Product product : products) {
            ProductImage mainImage = productImageDAO.getMainImageByProductId(product.getProductID());
            List<ProductImage> images = new ArrayList<>();
            if (mainImage != null) {
                images.add(mainImage);
            }
            product.setImages(images); // Use existing images field
        }

        request.setAttribute("products", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalProducts", totalProducts);
        request.getRequestDispatcher("/WEB-INF/views/productList.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr == null || productIdStr.isEmpty()) {
            LOGGER.warn("Missing product ID in edit request");
            response.sendRedirect(request.getContextPath() + "/products?error=Missing product ID");
            return;
        }

        try {
            UUID productId = UUID.fromString(productIdStr);
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                LOGGER.warn("Product not found for ID: {}", productId);
                response.sendRedirect(request.getContextPath() + "/products?error=Product not found");
                return;
            }

            List<ProductImage> images = productImageDAO.getImagesByProductId(productId);
            request.setAttribute("product", product);
            request.setAttribute("images", images);
            request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid product ID format: {}", productIdStr);
            response.sendRedirect(request.getContextPath() + "/products?error=Invalid product ID");
        }
    }

    private void showProductDetails(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr == null || productIdStr.isEmpty()) {
            LOGGER.warn("Missing product ID in details request");
            response.sendRedirect(request.getContextPath() + "/products?error=Missing product ID");
            return;
        }

        try {
            UUID productId = UUID.fromString(productIdStr);
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                LOGGER.warn("Product not found for ID: {}", productId);
                response.sendRedirect(request.getContextPath() + "/products?error=Product not found");
                return;
            }

            List<ProductImage> images = productImageDAO.getImagesByProductId(productId);
            request.setAttribute("product", product);
            request.setAttribute("images", images);
            request.getRequestDispatcher("/WEB-INF/views/productDetails.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid product ID format: {}", productIdStr);
            response.sendRedirect(request.getContextPath() + "/products?error=Invalid product ID");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Product product = parseProductFromRequest(request, true);
        if (product == null) {
            request.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
            return;
        }

        UUID productId = productDAO.addProduct(product);
        if (productId != null) {
            List<Part> imageParts = new ArrayList<>();
            for (Part part : request.getParts()) {
                if (part.getName().equals("imageFiles") && part.getSize() > 0) {
                    imageParts.add(part);
                }
            }

            for (int i = 0; i < imageParts.size(); i++) {
                Part filePart = imageParts.get(i);
                if (!isValidImageType(filePart)) {
                    request.setAttribute("error", "Invalid image type for file: " + filePart.getSubmittedFileName());
                    request.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
                    return;
                }

                ProductImage image = new ProductImage();
                image.setImageID(UUID.randomUUID());
                image.setProductID(productId);
                image.setAltText(sanitizeInput(request.getParameter("altText" + (i + 1))));
                image.setDisplayOrder(i + 1);
                image.setMainImage(i == 0); // First image is main
                image.setCreatedDate(LocalDateTime.now());
                image.setImageURL(filePart.getSubmittedFileName());

                try (InputStream imageContent = filePart.getInputStream()) {
                    if (!productImageDAO.addProductImage(image, imageContent)) {
                        LOGGER.warn("Failed to add image for product ID: {}", productId);
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/products?success=Product added successfully");
        } else {
            request.setAttribute("error", "Failed to add product");
            request.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(request, response);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String productIdStr = request.getParameter("productId");
        if (productIdStr == null || productIdStr.isEmpty()) {
            LOGGER.warn("Missing product ID in update request");
            response.sendRedirect(request.getContextPath() + "/products?error=Missing product ID");
            return;
        }

        try {
            UUID productId = UUID.fromString(productIdStr);
            Product product = productDAO.getProductById(productId);
            if (product == null) {
                LOGGER.warn("Product not found for ID: {}", productId);
                response.sendRedirect(request.getContextPath() + "/products?error=Product not found");
                return;
            }

            Product updatedProduct = parseProductFromRequest(request, false);
            if (updatedProduct == null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp").forward(request, response);
                return;
            }
            updatedProduct.setProductID(productId);

            if (productDAO.updateProduct(updatedProduct)) {
                List<Part> imageParts = new ArrayList<>();
                for (Part part : request.getParts()) {
                    if (part.getName().equals("imageFiles") && part.getSize() > 0) {
                        imageParts.add(part);
                    }
                }

                for (int i = 0; i < imageParts.size(); i++) {
                    Part filePart = imageParts.get(i);
                    if (!isValidImageType(filePart)) {
                        request.setAttribute("error", "Invalid image type for file: " + filePart.getSubmittedFileName());
                        request.setAttribute("product", updatedProduct);
                        request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp").forward(request, response);
                        return;
                    }

                    ProductImage image = productImageDAO.getMainImageByProductId(productId);
                    if (image == null) {
                        image = new ProductImage();
                        image.setImageID(UUID.randomUUID());
                        image.setProductID(productId);
                        image.setMainImage(i == 0);
                    }
                    image.setAltText(sanitizeInput(request.getParameter("altText" + (i + 1))));
                    image.setDisplayOrder(i + 1);
                    image.setImageURL(filePart.getSubmittedFileName());

                    try (InputStream imageContent = filePart.getInputStream()) {
                        if (image.getImageID() == null) {
                            productImageDAO.addProductImage(image, imageContent);
                        } else {
                            productImageDAO.updateProductImage(image, imageContent);
                        }
                    }
                }
                response.sendRedirect(request.getContextPath() + "/products?success=Product updated successfully");
            } else {
                request.setAttribute("error", "Failed to update product");
                request.setAttribute("product", updatedProduct);
                request.getRequestDispatcher("/WEB-INF/views/editProduct.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid product ID format: {}", productIdStr);
            response.sendRedirect(request.getContextPath() + "/products?error=Invalid product ID");
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String productIdStr = request.getParameter("id");
        if (productIdStr == null || productIdStr.isEmpty()) {
            LOGGER.warn("Missing product ID in delete request");
            response.sendRedirect(request.getContextPath() + "/products?error=Missing product ID");
            return;
        }

        try {
            UUID productId = UUID.fromString(productIdStr);
            List<ProductImage> images = productImageDAO.getImagesByProductId(productId);
            for (ProductImage image : images) {
                productImageDAO.deleteProductImage(image.getImageID());
            }
            if (productDAO.deleteProduct(productId)) {
                response.sendRedirect(request.getContextPath() + "/products?success=Product deleted successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/products?error=Failed to delete product");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid product ID format: {}", productIdStr);
            response.sendRedirect(request.getContextPath() + "/products?error=Invalid product ID");
        }
    }

    private Product parseProductFromRequest(HttpServletRequest request, boolean isAdd) throws IllegalArgumentException {
        Product product = new Product();
        String error = validateProductParameters(request);
        if (error != null) {
            request.setAttribute("error", error);
            return null;
        }

        try {
            product.setProductName(sanitizeInput(request.getParameter("productName")));
            product.setSku(sanitizeInput(request.getParameter("sku")));
            String categoryIdStr = request.getParameter("categoryId");
            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                product.setCategoryID(UUID.fromString(categoryIdStr));
            }
            String brandIdStr = request.getParameter("brandId");
            if (brandIdStr != null && !brandIdStr.isEmpty()) {
                product.setBrandID(UUID.fromString(brandIdStr));
            }
            product.setDescription(sanitizeInput(request.getParameter("description")));
            product.setShortDescription(sanitizeInput(request.getParameter("shortDescription")));
            String priceStr = request.getParameter("price");
            if (priceStr != null && !priceStr.isEmpty()) {
                product.setPrice(new BigDecimal(priceStr));
            }
            String comparePriceStr = request.getParameter("comparePrice");
            if (comparePriceStr != null && !comparePriceStr.isEmpty()) {
                product.setComparePrice(new BigDecimal(comparePriceStr));
            }
            String costPriceStr = request.getParameter("costPrice");
            if (costPriceStr != null && !costPriceStr.isEmpty()) {
                product.setCostPrice(new BigDecimal(costPriceStr));
            }
            String weightStr = request.getParameter("weight");
            if (weightStr != null && !weightStr.isEmpty()) {
                product.setWeight(new BigDecimal(weightStr));
            }
            product.setDimensions(sanitizeInput(request.getParameter("dimensions")));
            String stockQuantityStr = request.getParameter("stockQuantity");
            if (stockQuantityStr != null && !stockQuantityStr.isEmpty()) {
                product.setStockQuantity(Integer.parseInt(stockQuantityStr));
            }
            String minStockLevelStr = request.getParameter("minStockLevel");
            if (minStockLevelStr != null && !minStockLevelStr.isEmpty()) {
                product.setMinStockLevel(Integer.parseInt(minStockLevelStr));
            }
            String maxStockLevelStr = request.getParameter("maxStockLevel");
            if (maxStockLevelStr != null && !maxStockLevelStr.isEmpty()) {
                product.setMaxStockLevel(Integer.parseInt(maxStockLevelStr));
            }
            product.setActive(request.getParameter("isActive") != null && request.getParameter("isActive").equals("on"));
            product.setFeatured(request.getParameter("isFeatured") != null && request.getParameter("isFeatured").equals("on"));
            if (isAdd) {
                product.setCreatedDate(LocalDateTime.now());
            }
            product.setModifiedDate(LocalDateTime.now());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input data: " + e.getMessage());
        }
        return product;
    }

    private String validateProductParameters(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        String sku = request.getParameter("sku");
        String priceStr = request.getParameter("price");

        if (productName == null || productName.trim().isEmpty()) {
            return "Product name is required";
        }
        if (sku == null || sku.trim().isEmpty()) {
            return "SKU is required";
        }
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return "Price is required";
        }
        try {
            new BigDecimal(priceStr);
        } catch (NumberFormatException e) {
            return "Invalid price format";
        }
        return null;
    }

    private boolean isValidImageType(Part filePart) {
        String contentType = filePart.getContentType();
        return ALLOWED_IMAGE_TYPES.contains(contentType);
    }

    private String sanitizeInput(String input) {
        if (input == null) return null;
        // Basic sanitization to prevent XSS (replace with OWASP Java Encoder in production)
        return input.replaceAll("[<>\"&]", "");
    }
}