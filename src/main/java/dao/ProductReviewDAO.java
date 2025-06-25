package dao;

import dto.ProductReviewResponseDTO; // Import DTO mới
import model.ProductReview;
import util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductReviewDAO extends DBContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewDAO.class);

    /**
     * Thêm một đánh giá mới vào CSDL.
     * Phương thức này vẫn nhận vào model.ProductReview để thể hiện đúng bản chất dữ liệu cần lưu.
     * @param review Đối tượng ProductReview để thêm.
     * @return true nếu thêm thành công, false nếu thất bại.
     */
    public boolean addReview(ProductReview review) throws SQLException {
        String sql = "INSERT INTO ProductReviews (ProductID, UserID, Rating, Title, ReviewText, IsVerifiedPurchase, IsPublished, CreatedDate, ModifiedDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, review.getProductID().toString());
            stmt.setString(2, review.getUserID().toString());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getTitle());
            stmt.setString(5, review.getReviewText());
            stmt.setBoolean(6, review.isVerifiedPurchase());
            stmt.setBoolean(7, review.isPublished());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Successfully added review for product ID {}", review.getProductID());
                // Cân nhắc gọi trigger hoặc một stored procedure để cập nhật điểm trung bình
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to add review for product ID {}: {}", review.getProductID(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Lấy tất cả các đánh giá đã được duyệt cho một sản phẩm dưới dạng DTO.
     * @param productId ID của sản phẩm.
     * @return Danh sách các DTO đánh giá.
     */
    public List<ProductReviewResponseDTO> getPublishedReviewsByProductId(UUID productId) throws SQLException {
        List<ProductReviewResponseDTO> reviewDTOs = new ArrayList<>();
        // JOIN với bảng Users để lấy thông tin người đánh giá
        String sql = "SELECT pr.ReviewID, pr.ProductID, pr.UserID, pr.Rating, pr.Title, pr.ReviewText, " +
                "pr.IsVerifiedPurchase, pr.IsPublished, pr.HelpfulCount, pr.CreatedDate, " +
                "u.Username, u.FirstName, u.LastName " +
                "FROM ProductReviews pr " +
                "JOIN Users u ON pr.UserID = u.UserID " +
                "WHERE pr.ProductID = ? AND pr.IsPublished = 1 " +
                "ORDER BY pr.CreatedDate DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviewDTOs.add(mapResultSetToReviewDTO(rs));
                }
            }
            LOGGER.info("Retrieved {} published reviews as DTOs for product ID {}", reviewDTOs.size(), productId);
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve review DTOs for product ID {}: {}", productId, e.getMessage(), e);
            throw e;
        }
        return reviewDTOs;
    }

    /**
     * Phương thức helper để ánh xạ ResultSet sang ProductReviewResponseDTO.
     */
    private ProductReviewResponseDTO mapResultSetToReviewDTO(ResultSet rs) throws SQLException {
        ProductReviewResponseDTO dto = new ProductReviewResponseDTO();

        // Map các trường từ ProductReviews
        dto.setReviewID(UUID.fromString(rs.getString("ReviewID")));
        dto.setProductID(UUID.fromString(rs.getString("ProductID")));
        dto.setRating(rs.getInt("Rating"));
        dto.setTitle(rs.getString("Title"));
        dto.setReviewText(rs.getString("ReviewText"));
        dto.setVerifiedPurchase(rs.getBoolean("IsVerifiedPurchase"));
        dto.setHelpfulCount(rs.getInt("HelpfulCount"));
        dto.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());

        // Map các trường từ Users
        dto.setUserID(UUID.fromString(rs.getString("UserID")));
        dto.setUsername(rs.getString("Username"));

        String firstName = rs.getString("FirstName");
        String lastName = rs.getString("LastName");
        dto.setUserFullName((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : ""));

        return dto;
    }

    // Trong lớp dao.ProductReviewDAO

    /**
     * Lấy TẤT CẢ các đánh giá (cả đã duyệt và chưa duyệt) cho trang quản trị.
     * @return Danh sách DTO của tất cả các đánh giá.
     */
    public List<ProductReviewResponseDTO> getAllReviews() throws SQLException {
        List<ProductReviewResponseDTO> reviewDTOs = new ArrayList<>();
        String sql = "SELECT pr.ReviewID, pr.ProductID, pr.UserID, pr.Rating, pr.Title, pr.ReviewText, " +
                "pr.IsVerifiedPurchase, pr.IsPublished, pr.HelpfulCount, pr.CreatedDate, " +
                "u.Username, u.FirstName, u.LastName " +
                "FROM ProductReviews pr " +
                "JOIN Users u ON pr.UserID = u.UserID " +
                "ORDER BY pr.CreatedDate DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reviewDTOs.add(mapResultSetToReviewDTO(rs)); // Dùng lại phương thức map đã có
            }
            LOGGER.info("Retrieved all {} reviews for admin.", reviewDTOs.size());
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve all reviews for admin: {}", e.getMessage(), e);
            throw e;
        }
        return reviewDTOs;
    }

    /**
     * Đảo ngược trạng thái duyệt (IsPublished) của một đánh giá.
     * @param reviewId ID của đánh giá cần thay đổi.
     * @return true nếu thành công.
     */
    public boolean togglePublishStatus(UUID reviewId) throws SQLException {
        // Lấy ProductID trước để cập nhật rating sau
        UUID productId = getProductIdByReviewId(reviewId);
        if (productId == null) return false;

        String sql = "UPDATE ProductReviews SET IsPublished = CASE WHEN IsPublished = 1 THEN 0 ELSE 1 END WHERE ReviewID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reviewId.toString());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateProductRating(productId); // Cập nhật lại điểm trung bình
                LOGGER.info("Toggled publish status for review ID {}", reviewId);
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to toggle publish status for review ID {}: {}", reviewId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Xóa vĩnh viễn một đánh giá khỏi CSDL.
     * @param reviewId ID của đánh giá cần xóa.
     * @return true nếu xóa thành công.
     */
    public boolean deleteReview(UUID reviewId) throws SQLException {
        // Lấy ProductID trước để cập nhật rating sau khi xóa
        UUID productId = getProductIdByReviewId(reviewId);
        if (productId == null) return false;

        String sql = "DELETE FROM ProductReviews WHERE ReviewID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reviewId.toString());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                updateProductRating(productId); // Cập nhật lại điểm trung bình
                LOGGER.info("Permanently deleted review ID {}", reviewId);
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete review ID {}: {}", reviewId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Helper method để lấy ProductID từ một ReviewID.
     * Cần thiết cho việc cập nhật rating sau khi thay đổi trạng thái hoặc xóa.
     */
    private UUID getProductIdByReviewId(UUID reviewId) throws SQLException {
        String sql = "SELECT ProductID FROM ProductReviews WHERE ReviewID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, reviewId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("ProductID"));
            }
        }
        return null;
    }

    // Đặt phương thức này bên trong lớp public class ProductReviewDAO extends DBContext { ... }

    /**
     * Cập nhật điểm đánh giá trung bình và tổng số lượng đánh giá cho một sản phẩm cụ thể.
     * Phương thức này nên được gọi sau mỗi lần thêm, sửa đổi trạng thái (publish/unpublish), hoặc xóa một đánh giá.
     *
     * @param productId ID của sản phẩm cần cập nhật.
     * @throws SQLException Nếu có lỗi khi truy cập CSDL.
     */
    public void updateProductRating(UUID productId) throws SQLException {
        // Câu lệnh SQL này sử dụng các hàm tổng hợp (aggregate functions) để tính toán lại các giá trị.
        // AVG(CAST(Rating AS DECIMAL(3,2))) để tính trung bình với độ chính xác.
        // COUNT(*) để đếm số lượng review đã được duyệt.
        // ISNULL(..., 0) để đảm bảo nếu không có review nào, giá trị sẽ là 0 thay vì NULL.
        String sql = "UPDATE Products " +
                "SET " +
                "    AverageRating = ISNULL((SELECT AVG(CAST(Rating AS DECIMAL(3,2))) FROM ProductReviews WHERE ProductID = ? AND IsPublished = 1), 0), " +
                "    ReviewCount = ISNULL((SELECT COUNT(*) FROM ProductReviews WHERE ProductID = ? AND IsPublished = 1), 0) " +
                "WHERE ProductID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Gán tham số productId cho cả 3 vị trí `?` trong câu lệnh SQL
            stmt.setString(1, productId.toString());
            stmt.setString(2, productId.toString());
            stmt.setString(3, productId.toString());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                LOGGER.info("Successfully updated rating stats for product ID: {}", productId);
            } else {
                LOGGER.warn("Attempted to update rating stats, but product with ID {} was not found.", productId);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to update product rating stats for ID {}: {}", productId, e.getMessage(), e);
            // Ném lại ngoại lệ để tầng trên có thể xử lý (ví dụ: rollback transaction)
            throw e;
        }
    }
}