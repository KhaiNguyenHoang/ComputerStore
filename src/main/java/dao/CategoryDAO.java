package dao;

import model.Category;
import util.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDAO extends DBContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDAO.class);

    /**
     * Lấy tất cả các danh mục từ CSDL, bao gồm cả các danh mục không hoạt động.
     * @return danh sách các đối tượng Category.
     */
    public List<Category> getAllCategoriesForAdmin() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName, Description, ParentCategoryID, IsActive, CreatedDate, ModifiedDate " +
                "FROM Categories ORDER BY CategoryName";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
            LOGGER.info("Retrieved {} categories for admin.", categories.size());
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve categories for admin: {}", e.getMessage(), e);
            throw e;
        }
        return categories;
    }

    /**
     * Lấy một danh mục cụ thể bằng ID (UUID).
     * @param categoryId ID của danh mục cần tìm.
     * @return đối tượng Category hoặc null nếu không tìm thấy.
     */
    public Category getCategoryById(UUID categoryId) throws SQLException {
        String sql = "SELECT CategoryID, CategoryName, Description, ParentCategoryID, IsActive, CreatedDate, ModifiedDate " +
                "FROM Categories WHERE CategoryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LOGGER.info("Retrieved category with ID: {}", categoryId);
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to retrieve category with ID {}: {}", categoryId, e.getMessage(), e);
            throw e;
        }
        LOGGER.warn("No category found with ID: {}", categoryId);
        return null;
    }

    /**
     * Thêm một danh mục mới vào CSDL.
     * @param category đối tượng Category chứa thông tin cần thêm.
     * @return UUID của danh mục vừa được tạo, hoặc null nếu thất bại.
     */
    public UUID addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO Categories (CategoryName, Description, ParentCategoryID, IsActive, ModifiedDate) " +
                "OUTPUT INSERTED.CategoryID " +
                "VALUES (?, ?, ?, ?, GETDATE())";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            if (category.getParentCategoryID() != null) {
                stmt.setString(3, category.getParentCategoryID().toString());
            } else {
                stmt.setObject(3, null);
            }
            stmt.setBoolean(4, category.isActive());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UUID newId = UUID.fromString(rs.getString(1));
                    LOGGER.info("Successfully added category '{}' with new ID: {}", category.getCategoryName(), newId);
                    return newId;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to add category '{}': {}", category.getCategoryName(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    /**
     * Cập nhật thông tin một danh mục đã có.
     * @param category đối tượng Category chứa thông tin cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE Categories SET CategoryName = ?, Description = ?, ParentCategoryID = ?, IsActive = ?, ModifiedDate = GETDATE() " +
                "WHERE CategoryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            if (category.getParentCategoryID() != null) {
                stmt.setString(3, category.getParentCategoryID().toString());
            } else {
                stmt.setObject(3, null);
            }
            stmt.setBoolean(4, category.isActive());
            stmt.setString(5, category.getCategoryID().toString());

            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Update attempt for category ID {}. Rows affected: {}", category.getCategoryID(), rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update category with ID {}: {}", category.getCategoryID(), e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Xóa một danh mục (xóa mềm bằng cách set IsActive = 0).
     * @param categoryId ID của danh mục cần xóa.
     * @return true nếu xóa thành công, false nếu thất bại.
     */
    public boolean deleteCategory(UUID categoryId) throws SQLException {
        String sql = "UPDATE Categories SET IsActive = 0, ModifiedDate = GETDATE() WHERE CategoryID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId.toString());
            int rowsAffected = stmt.executeUpdate();
            LOGGER.info("Soft delete attempt for category ID {}. Rows affected: {}", categoryId, rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete category with ID {}: {}", categoryId, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Phương thức helper để ánh xạ một hàng từ ResultSet sang đối tượng Category.
     */
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryID(UUID.fromString(rs.getString("CategoryID")));
        category.setCategoryName(rs.getString("CategoryName"));
        category.setDescription(rs.getString("Description"));

        String parentIdStr = rs.getString("ParentCategoryID");
        if (parentIdStr != null) {
            category.setParentCategoryID(UUID.fromString(parentIdStr));
        }

        category.setActive(rs.getBoolean("IsActive"));

        if (rs.getTimestamp("CreatedDate") != null) {
            category.setCreatedDate(rs.getTimestamp("CreatedDate").toLocalDateTime());
        }
        if (rs.getTimestamp("ModifiedDate") != null) {
            category.setModifiedDate(rs.getTimestamp("ModifiedDate").toLocalDateTime());
        }
        return category;
    }
}