package dao;

import model.Brand;
import model.Category;
import model.Product;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.math.BigDecimal;

public class ProductDAO extends DBContext {
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    public List<Product> getProducts(int page, int pageSize) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.ProductID, p.ProductName, p.SKU, p.CategoryID, c.CategoryName, " +
                "p.BrandID, b.BrandName, p.Description, p.ShortDescription, p.Price, " +
                "p.ComparePrice, p.CostPrice, p.Weight, p.Dimensions, p.StockQuantity, " +
                "p.MinStockLevel, p.MaxStockLevel, p.IsActive, p.IsFeatured, p.ViewCount, " +
                "p.SalesCount, p.AverageRating, p.ReviewCount, p.CreatedDate, p.ModifiedDate " +
                "FROM Products p " +
                "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                "LEFT JOIN Brands b ON p.BrandID = b.BrandID " +
                "WHERE p.IsActive = 1 " +
                "ORDER BY p.ProductName " +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize;
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = null;
                    if (rs.getString("CategoryID") != null) {
                        category = new Category();
                        category.setCategoryID(UUID.fromString(rs.getString("CategoryID")));
                        category.setCategoryName(rs.getString("CategoryName"));
                    }

                    Brand brand = null;
                    if (rs.getString("BrandID") != null) {
                        brand = new Brand();
                        brand.setBrandID(UUID.fromString(rs.getString("BrandID")));
                        brand.setBrandName(rs.getString("BrandName"));
                    }

                    Product product = new Product();
                    product.setProductID(rs.getString("ProductID") != null ? UUID.fromString(rs.getString("ProductID")) : null);
                    product.setProductName(rs.getString("ProductName"));
                    product.setSku(rs.getString("SKU"));
                    product.setCategoryID(rs.getString("CategoryID") != null ? UUID.fromString(rs.getString("CategoryID")) : null);
                    product.setBrandID(rs.getString("BrandID") != null ? UUID.fromString(rs.getString("BrandID")) : null);
                    product.setCategory(category);
                    product.setBrand(brand);
                    product.setDescription(rs.getString("Description"));
                    product.setShortDescription(rs.getString("ShortDescription"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setComparePrice(rs.getBigDecimal("ComparePrice"));
                    product.setCostPrice(rs.getBigDecimal("CostPrice"));
                    product.setWeight(rs.getBigDecimal("Weight"));
                    product.setDimensions(rs.getString("Dimensions"));
                    product.setStockQuantity(rs.getInt("StockQuantity"));
                    product.setMinStockLevel(rs.getInt("MinStockLevel"));
                    product.setMaxStockLevel(rs.getInt("MaxStockLevel"));
                    product.setActive(rs.getBoolean("IsActive"));
                    product.setFeatured(rs.getBoolean("IsFeatured"));
                    product.setViewCount(rs.getInt("ViewCount"));
                    product.setSalesCount(rs.getInt("SalesCount"));
                    product.setAverageRating(rs.getBigDecimal("AverageRating"));
                    product.setReviewCount(rs.getInt("ReviewCount"));
                    Timestamp createdDate = rs.getTimestamp("CreatedDate");
                    product.setCreatedDate(createdDate != null ? createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
                    Timestamp modifiedDate = rs.getTimestamp("ModifiedDate");
                    product.setModifiedDate(modifiedDate != null ? modifiedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);

                    products.add(product);
                }
            }
            logger.info("Retrieved " + products.size() + " products for page " + page);
        } catch (SQLException e) {
            logger.severe("Failed to get products: " + e.getMessage());
            throw new SQLException("Error retrieving products: " + e.getMessage(), e);
        }
        return products;
    }

    public int getTotalProducts() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Products WHERE IsActive = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.severe("Failed to get total products: " + e.getMessage());
            throw new SQLException("Error retrieving total products: " + e.getMessage(), e);
        }
        return 0;
    }

    public UUID addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO Products (ProductID, ProductName, SKU, CategoryID, BrandID, Description, " +
                "ShortDescription, Price, ComparePrice, CostPrice, Weight, Dimensions, StockQuantity, " +
                "MinStockLevel, MaxStockLevel, IsActive, IsFeatured, ViewCount, SalesCount, " +
                "AverageRating, ReviewCount, CreatedDate, ModifiedDate) " +
                "OUTPUT INSERTED.ProductID " +
                "VALUES (NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getSku());
            stmt.setObject(3, product.getCategoryID() != null ? product.getCategoryID().toString() : null);
            stmt.setObject(4, product.getBrandID() != null ? product.getBrandID().toString() : null);
            stmt.setString(5, product.getDescription());
            stmt.setString(6, product.getShortDescription());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setBigDecimal(8, product.getComparePrice());
            stmt.setBigDecimal(9, product.getCostPrice());
            stmt.setBigDecimal(10, product.getWeight());
            stmt.setString(11, product.getDimensions());
            stmt.setInt(12, product.getStockQuantity());
            stmt.setInt(13, product.getMinStockLevel());
            stmt.setInt(14, product.getMaxStockLevel());
            stmt.setBoolean(15, product.isActive());
            stmt.setBoolean(16, product.isFeatured());
            stmt.setInt(17, product.getViewCount());
            stmt.setInt(18, product.getSalesCount());
            stmt.setBigDecimal(19, product.getAverageRating());
            stmt.setInt(20, product.getReviewCount());
            stmt.setTimestamp(21, product.getCreatedDate() != null ?
                    Timestamp.valueOf(product.getCreatedDate()) : Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(22, product.getModifiedDate() != null ?
                    Timestamp.valueOf(product.getModifiedDate()) : Timestamp.valueOf(LocalDateTime.now()));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String productIdStr = rs.getString(1);
                    if (productIdStr != null) {
                        UUID productId = UUID.fromString(productIdStr);
                        logger.info("Successfully added product: " + product.getProductName() + " with ID: " + productId);
                        return productId;
                    }
                }
            }
            logger.warning("No product was added for: " + product.getProductName());
            return null;
        } catch (SQLException e) {
            logger.severe("Failed to add product: " + product.getProductName() + ". Error: " + e.getMessage());
            throw new SQLException("Error adding product: " + e.getMessage(), e);
        }
    }

    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE Products SET ProductName = ?, SKU = ?, CategoryID = ?, BrandID = ?, Description = ?, " +
                "ShortDescription = ?, Price = ?, ComparePrice = ?, CostPrice = ?, Weight = ?, Dimensions = ?, " +
                "StockQuantity = ?, MinStockLevel = ?, MaxStockLevel = ?, IsActive = ?, IsFeatured = ?, " +
                "ViewCount = ?, SalesCount = ?, AverageRating = ?, ReviewCount = ?, ModifiedDate = ? " +
                "WHERE ProductID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getSku());
            stmt.setObject(3, product.getCategoryID() != null ? product.getCategoryID().toString() : null);
            stmt.setObject(4, product.getBrandID() != null ? product.getBrandID().toString() : null);
            stmt.setString(5, product.getDescription());
            stmt.setString(6, product.getShortDescription());
            stmt.setBigDecimal(7, product.getPrice());
            stmt.setBigDecimal(8, product.getComparePrice());
            stmt.setBigDecimal(9, product.getCostPrice());
            stmt.setBigDecimal(10, product.getWeight());
            stmt.setString(11, product.getDimensions());
            stmt.setInt(12, product.getStockQuantity());
            stmt.setInt(13, product.getMinStockLevel());
            stmt.setInt(14, product.getMaxStockLevel());
            stmt.setBoolean(15, product.isActive());
            stmt.setBoolean(16, product.isFeatured());
            stmt.setInt(17, product.getViewCount());
            stmt.setInt(18, product.getSalesCount());
            stmt.setBigDecimal(19, product.getAverageRating());
            stmt.setInt(20, product.getReviewCount());
            stmt.setTimestamp(21, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(22, product.getProductID().toString());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Successfully updated product: " + product.getProductName() + " with ID: " + product.getProductID());
                return true;
            } else {
                logger.warning("No product updated for ID: " + product.getProductID());
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Failed to update product: " + product.getProductName() + ". Error: " + e.getMessage());
            throw new SQLException("Error updating product: " + e.getMessage(), e);
        }
    }

    public boolean deleteProduct(UUID productId) throws SQLException {
        String sql = "UPDATE Products SET IsActive = 0, ModifiedDate = ? WHERE ProductID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, productId.toString());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Successfully deleted product with ID: " + productId);
                return true;
            } else {
                logger.warning("No product deleted for ID: " + productId);
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Failed to delete product with ID: " + productId + ". Error: " + e.getMessage());
            throw new SQLException("Error deleting product: " + e.getMessage(), e);
        }
    }

    public Product getProductById(UUID productId) throws SQLException {
        String sql = "SELECT p.ProductID, p.ProductName, p.SKU, p.CategoryID, c.CategoryName, " +
                "p.BrandID, b.BrandName, p.Description, p.ShortDescription, p.Price, " +
                "p.ComparePrice, p.CostPrice, p.Weight, p.Dimensions, p.StockQuantity, " +
                "p.MinStockLevel, p.MaxStockLevel, p.IsActive, p.IsFeatured, p.ViewCount, " +
                "p.SalesCount, p.AverageRating, p.ReviewCount, p.CreatedDate, p.ModifiedDate " +
                "FROM Products p " +
                "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                "LEFT JOIN Brands b ON p.BrandID = b.BrandID " +
                "WHERE p.ProductID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = null;
                    if (rs.getString("CategoryID") != null) {
                        category = new Category();
                        category.setCategoryID(UUID.fromString(rs.getString("CategoryID")));
                        category.setCategoryName(rs.getString("CategoryName"));
                    }

                    Brand brand = null;
                    if (rs.getString("BrandID") != null) {
                        brand = new Brand();
                        brand.setBrandID(UUID.fromString(rs.getString("BrandID")));
                        brand.setBrandName(rs.getString("BrandName"));
                    }

                    Product product = new Product();
                    product.setProductID(rs.getString("ProductID") != null ? UUID.fromString(rs.getString("ProductID")) : null);
                    product.setProductName(rs.getString("ProductName"));
                    product.setSku(rs.getString("SKU"));
                    product.setCategoryID(rs.getString("CategoryID") != null ? UUID.fromString(rs.getString("CategoryID")) : null);
                    product.setBrandID(rs.getString("BrandID") != null ? UUID.fromString(rs.getString("BrandID")) : null);
                    product.setCategory(category);
                    product.setBrand(brand);
                    product.setDescription(rs.getString("Description"));
                    product.setShortDescription(rs.getString("ShortDescription"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setComparePrice(rs.getBigDecimal("ComparePrice"));
                    product.setCostPrice(rs.getBigDecimal("CostPrice"));
                    product.setWeight(rs.getBigDecimal("Weight"));
                    product.setDimensions(rs.getString("Dimensions"));
                    product.setStockQuantity(rs.getInt("StockQuantity"));
                    product.setMinStockLevel(rs.getInt("MinStockLevel"));
                    product.setMaxStockLevel(rs.getInt("MaxStockLevel"));
                    product.setActive(rs.getBoolean("IsActive"));
                    product.setFeatured(rs.getBoolean("IsFeatured"));
                    product.setViewCount(rs.getInt("ViewCount"));
                    product.setSalesCount(rs.getInt("SalesCount"));
                    product.setAverageRating(rs.getBigDecimal("AverageRating"));
                    product.setReviewCount(rs.getInt("ReviewCount"));
                    Timestamp createdDate = rs.getTimestamp("CreatedDate");
                    product.setCreatedDate(createdDate != null ? createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);
                    Timestamp modifiedDate = rs.getTimestamp("ModifiedDate");
                    product.setModifiedDate(modifiedDate != null ? modifiedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null);

                    logger.info("Retrieved product with ID: " + productId);
                    return product;
                }
            }
            logger.warning("No product found with ID: " + productId);
            return null;
        } catch (SQLException e) {
            logger.severe("Failed to retrieve product with ID: " + productId + ". Error: " + e.getMessage());
            throw new SQLException("Error retrieving product: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        try {
            // Step 1: Fetch a valid product to work with (first active product)
            List<Product> products = dao.getProducts(1, 1);
            if (products.isEmpty()) {
                System.out.println("No active products found in the database.");
                return;
            }
            UUID productId = products.get(0).getProductID();
            System.out.println("\nStep 1: Fetching product details");
            Product product = dao.getProductById(productId);
            if (product != null) {
                System.out.println("Product: " + product.getProductName() + " (SKU: " + product.getSku() + ")");
                System.out.println("Price: " + product.getPrice());
                System.out.println("Stock Quantity: " + product.getStockQuantity());
                System.out.println("Category: " + (product.getCategory() != null ? product.getCategory().getCategoryName() : "None"));
                System.out.println("Brand: " + (product.getBrand() != null ? product.getBrand().getBrandName() : "None"));
            } else {
                System.out.println("Failed to retrieve product with ID: " + productId);
                return;
            }

            // Step 2: Update the product
            System.out.println("\nStep 2: Updating product");
            product.setProductName(product.getProductName() + " (Updated)");
            product.setPrice(product.getPrice().add(new BigDecimal("50.00")));
            product.setStockQuantity(product.getStockQuantity() + 10);
            product.setModifiedDate(LocalDateTime.now());
            if (dao.updateProduct(product)) {
                System.out.println("Product updated successfully.");
                Product updatedProduct = dao.getProductById(productId);
                if (updatedProduct != null) {
                    System.out.println("Updated Product: " + updatedProduct.getProductName() + " (SKU: " + updatedProduct.getSku() + ")");
                    System.out.println("Updated Price: " + updatedProduct.getPrice());
                    System.out.println("Updated Stock Quantity: " + updatedProduct.getStockQuantity());
                }
            } else {
                System.out.println("Failed to update product with ID: " + productId);
            }

            // Step 3: Delete the product (soft delete)
            System.out.println("\nStep 3: Deleting product");
            if (dao.deleteProduct(productId)) {
                System.out.println("Product deleted successfully.");
                Product deletedProduct = dao.getProductById(productId);
                if (deletedProduct == null || !deletedProduct.isActive()) {
                    System.out.println("Confirmed: Product is no longer active.");
                } else {
                    System.out.println("Product still appears active.");
                }
            } else {
                System.out.println("Failed to delete product with ID: " + productId);
            }

            // Step 4: List remaining active products
            System.out.println("\nStep 4: Listing remaining active products");
            products = dao.getProducts(1, 10);
            if (products.isEmpty()) {
                System.out.println("No active products found.");
            } else {
                for (Product p : products) {
                    System.out.println(p.getProductName() + " (SKU: " + p.getSku() + ")");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}