package dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) để truyền dữ liệu đánh giá sản phẩm ra tầng View.
 * Lớp này kết hợp thông tin từ ProductReview và thông tin cơ bản của User.
 */
public class ProductReviewResponseDTO {
    // Các trường từ ProductReview
    private UUID reviewID;
    private UUID productID;
    private int rating;
    private String title;
    private String reviewText;
    private boolean isVerifiedPurchase;
    private int helpfulCount;
    private LocalDateTime createdDate;

    // Các trường rút gọn từ User
    private UUID userID;
    private String username;
    private String userFullName; // Tên đầy đủ của người dùng

    // Constructors
    public ProductReviewResponseDTO() {}

    // Getters and Setters
    public UUID getReviewID() { return reviewID; }
    public void setReviewID(UUID reviewID) { this.reviewID = reviewID; }

    public UUID getProductID() { return productID; }
    public void setProductID(UUID productID) { this.productID = productID; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public boolean isVerifiedPurchase() { return isVerifiedPurchase; }
    public void setVerifiedPurchase(boolean verifiedPurchase) { this.isVerifiedPurchase = verifiedPurchase; }

    public int getHelpfulCount() { return helpfulCount; }
    public void setHelpfulCount(int helpfulCount) { this.helpfulCount = helpfulCount; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public UUID getUserID() { return userID; }
    public void setUserID(UUID userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
}