package controller.servlet;

import dao.ProductReviewDAO;
import model.ProductReview;
import model.User; // Giả sử User object được lưu trong session

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import dto.ProductReviewResponseDTO;

@WebServlet(urlPatterns = {
        "/reviews/add",                // Dành cho người dùng thêm đánh giá
        "/admin/reviews",              // Dành cho admin xem danh sách đánh giá
        "/admin/reviews/toggle",       // Dành cho admin duyệt/bỏ duyệt
        "/admin/reviews/delete"        // Dành cho admin xóa
})
public class ProductReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewServlet.class);
    private ProductReviewDAO reviewDAO;

    @Override
    public void init() {
        // Khởi tạo DAO (DBContext đã được xử lý bên trong)
        reviewDAO = new ProductReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/admin/reviews":
                    listAllReviews(request, response);
                    break;
                case "/admin/reviews/toggle":
                    togglePublishStatus(request, response);
                    break;
                case "/admin/reviews/delete":
                    deleteReview(request, response);
                    break;
                default:
                    // Các yêu cầu GET không xác định sẽ được chuyển hướng
                    response.sendRedirect(request.getContextPath() + "/");
                    break;
            }
        } catch (SQLException ex) {
            handleSQLException(ex, action, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            if ("/reviews/add".equals(action)) {
                addReviewFromUser(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } catch (SQLException ex) {
            handleSQLException(ex, action, request, response);
        } catch (Exception ex) {
            handleGenericException(ex, action, request, response);
        }
    }

    /**
     * Xử lý việc người dùng thêm đánh giá mới.
     */
    private void addReviewFromUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa có
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        String productIdStr = request.getParameter("productId");

        // Luôn chuyển hướng về trang sản phẩm dù thành công hay thất bại
        String redirectURL = request.getContextPath() + "/products/details?id=" + (productIdStr != null ? productIdStr : "");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + redirectURL);
            return;
        }

        String ratingStr = request.getParameter("rating");
        String title = request.getParameter("title");
        String reviewText = request.getParameter("reviewText");

        if (productIdStr == null || ratingStr == null || reviewText == null || reviewText.trim().isEmpty()) {
            response.sendRedirect(redirectURL + "&error=MissingReviewFields");
            return;
        }

        try {
            ProductReview review = new ProductReview();
            review.setProductID(UUID.fromString(productIdStr));
            review.setUserID(currentUser.getUserID());
            review.setRating(Integer.parseInt(ratingStr));
            review.setTitle(title);
            review.setReviewText(reviewText);
            review.setVerifiedPurchase(false); // Cần logic nghiệp vụ để xác định điều này
            review.setPublished(true); // Mặc định là duyệt, hoặc false nếu cần admin duyệt thủ công

            boolean success = reviewDAO.addReview(review);

            if (success) {
                response.sendRedirect(redirectURL + "&success=ReviewAdded");
            } else {
                response.sendRedirect(redirectURL + "&error=FailedToAddReview");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid review data submitted: {}", e.getMessage());
            response.sendRedirect(redirectURL + "&error=InvalidData");
        }
    }

    /**
     * Lấy danh sách tất cả các đánh giá cho trang quản trị.
     */
    private void listAllReviews(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        // Cần thêm một phương thức trong DAO để lấy TẤT CẢ reviews, không chỉ những cái đã duyệt
        // Ví dụ: List<ProductReviewResponseDTO> allReviews = reviewDAO.getAllReviews();
        // Tạm thời, ta dùng lại phương thức cũ và giả sử nó lấy tất cả

        // Để làm đúng, bạn cần tạo phương thức mới trong DAO:
        // public List<ProductReviewResponseDTO> getAllReviews() { ... }
        // Câu SQL sẽ không có điều kiện "AND pr.IsPublished = 1"

        // Giả sử đã có phương thức đó
        List<ProductReviewResponseDTO> allReviews = reviewDAO.getAllReviews(); // Giả sử có phương thức này
        request.setAttribute("reviews", allReviews);
        request.getRequestDispatcher("/WEB-INF/views/admin/reviewList.jsp").forward(request, response);
    }

    /**
     * Thay đổi trạng thái duyệt (published/unpublished) của một đánh giá.
     */
    private void togglePublishStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String reviewIdStr = request.getParameter("id");
        if (reviewIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews?error=MissingID");
            return;
        }

        try {
            UUID reviewId = UUID.fromString(reviewIdStr);
            // Cần thêm phương thức trong DAO: public boolean togglePublishStatus(UUID reviewId)
            boolean success = reviewDAO.togglePublishStatus(reviewId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?success=StatusChanged");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?error=UpdateFailed");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews?error=InvalidID");
        }
    }

    /**
     * Xóa vĩnh viễn một đánh giá.
     */
    private void deleteReview(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String reviewIdStr = request.getParameter("id");
        if (reviewIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews?error=MissingID");
            return;
        }

        try {
            UUID reviewId = UUID.fromString(reviewIdStr);
            // Cần thêm phương thức trong DAO: public boolean deleteReview(UUID reviewId)
            boolean success = reviewDAO.deleteReview(reviewId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?success=ReviewDeleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/reviews?error=DeleteFailed");
            }
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/admin/reviews?error=InvalidID");
        }
    }

    // --- Các phương thức xử lý lỗi chung ---

    private void handleSQLException(SQLException ex, String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("Database error in action {}: {}", action, ex.getMessage(), ex);
        request.setAttribute("errorMessage", "A database error occurred: " + ex.getMessage());
        // Chuyển hướng về trang tương ứng với lỗi
        if (action.startsWith("/admin")) {
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        } else {
            String productId = request.getParameter("productId");
            response.sendRedirect(request.getContextPath() + "/products/details?id=" + productId + "&error=DatabaseError");
        }
    }

    private void handleGenericException(Exception ex, String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.error("Unexpected error in action {}: {}", action, ex.getMessage(), ex);
        request.setAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        if (action.startsWith("/admin")) {
            request.getRequestDispatcher("/WEB-INF/views/admin/error.jsp").forward(request, response);
        } else {
            String productId = request.getParameter("productId");
            response.sendRedirect(request.getContextPath() + "/products/details?id=" + productId + "&error=UnexpectedError");
        }
    }
}