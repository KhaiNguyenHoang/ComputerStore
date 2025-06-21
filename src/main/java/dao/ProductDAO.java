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
                    // Create Category object
                    Category category = null;
                    if (rs.getString("CategoryID") != null) {
                        category = new Category();
                        category.setCategoryID(UUID.fromString(rs.getString("CategoryID")));
                        category.setCategoryName(rs.getString("CategoryName"));
                    }

                    // Create Brand object
                    Brand brand = null;
                    if (rs.getString("BrandID") != null) {
                        brand = new Brand();
                        brand.setBrandID(UUID.fromString(rs.getString("BrandID")));
                        brand.setBrandName(rs.getString("BrandName"));
                    }

                    // Create Product object
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
            // Set parameters
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

            // Execute the insert and get the generated ProductID
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

    public static void main(String[] args) throws SQLException {
        ProductDAO dao = new ProductDAO();
//        try {
//            // Fetch a valid CategoryID for 'Gaming Laptops'
//            String categorySql = "SELECT CategoryID FROM Categories WHERE CategoryName = ?";
//            UUID categoryId = null;
//            try (PreparedStatement stmt = dao.connection.prepareStatement(categorySql)) {
//                stmt.setString(1, "Gaming Laptops");
//                try (ResultSet rs = stmt.executeQuery()) {
//                    if (rs.next()) {
//                        categoryId = UUID.fromString(rs.getString("CategoryID"));
//                    } else {
//                        System.out.println("Error: Category 'Gaming Laptops' not found.");
//                        return;
//                    }
//                }
//            }
//
//            // Fetch a valid BrandID for 'ASUS'
//            String brandSql = "SELECT BrandID FROM Brands WHERE BrandName = ?";
//            UUID brandId = null;
//            try (PreparedStatement stmt = dao.connection.prepareStatement(brandSql)) {
//                stmt.setString(1, "ASUS");
//                try (ResultSet rs = stmt.executeQuery()) {
//                    if (rs.next()) {
//                        brandId = UUID.fromString(rs.getString("BrandID"));
//                    } else {
//                        System.out.println("Error: Brand 'ASUS' not found.");
//                        return;
//                    }
//                }
//            }
//
//            // Create a sample Product
//            Product product = new Product();
//            product.setProductName("ASUS TUF Gaming A15 Laptop");
//            product.setSku("ASUS-TUF-A15-002");
//            product.setCategoryID(categoryId);
//            product.setBrandID(brandId);
//            product.setDescription("High-performance gaming laptop with RTX 3060 and Ryzen 5 processor");
//            product.setShortDescription("Gaming laptop with RTX 3060");
//            product.setPrice(new BigDecimal("1099.99"));
//            product.setComparePrice(new BigDecimal("1299.99"));
//            product.setCostPrice(new BigDecimal("900.00"));
//            product.setWeight(new BigDecimal("2.3"));
//            product.setDimensions("35.9 x 25.6 x 2.28 cm");
//            product.setStockQuantity(30);
//            product.setMinStockLevel(5);
//            product.setMaxStockLevel(100);
//            product.setActive(true);
//            product.setFeatured(true);
//            product.setViewCount(0);
//            product.setSalesCount(0);
//            product.setAverageRating(new BigDecimal("0.0"));
//            product.setReviewCount(0);
//            product.setCreatedDate(LocalDateTime.now());
//            product.setModifiedDate(LocalDateTime.now());
//
//            // Add the product
//            UUID productId = dao.addProduct(product);
//            if (productId != null) {
//                System.out.println("Successfully added product: " + product.getProductName() + " with ID: " + productId);
//            } else {
//                System.out.println("Failed to add product: " + product.getProductName());
//            }
//
//            // Verify by listing products
//            List<Product> products = dao.getProducts(1, 10);
//            System.out.println("\nCurrent products in database:");
//            for (Product p : products) {
//                System.out.println(p.getProductName() + " (SKU: " + p.getSku() + ")");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error in demo: " + e.getMessage());
//            e.printStackTrace();
//        }
        List<Product> products = dao.getProducts(1, 10);
        for (Product p : products) {
            System.out.println(p.getProductName() + " (SKU: " + p.getSku() + ")");
            System.out.println("Category: " + p.getCategory().getCategoryName());
            System.out.println("Brand: " + p.getBrand().getBrandName());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Short Description: " + p.getShortDescription());
            System.out.println("Price: " + p.getPrice());
            System.out.println("Compare Price: " + p.getComparePrice());
            System.out.println("Cost Price: " + p.getCostPrice());
            System.out.println("Weight: " + p.getWeight());
        }
    }
}