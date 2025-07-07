<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>${product.productName} - Chi tiết sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 960px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .product-detail { display: flex; gap: 30px; }
        .product-image { flex: 1; text-align: center; }
        .product-image img { max-width: 100%; height: auto; border: 1px solid #ddd; border-radius: 5px; }
        .product-info { flex: 2; }
        .product-info h2 { color: #007bff; margin-top: 0; }
        .product-info .price { font-size: 2em; color: #dc3545; font-weight: bold; margin-bottom: 15px; }
        .product-info p { margin-bottom: 10px; line-height: 1.6; }
        .product-info .label { font-weight: bold; color: #555; }
        .back-link { display: block; text-align: center; margin-top: 30px; }
        .back-link a { color: #007bff; text-decoration: none; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
        .add-to-cart-form { display: flex; align-items: center; gap: 10px; margin-top: 20px; }
        .quantity-input { width: 60px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; text-align: center; font-size: 1em; }
        .add-to-cart-btn { background-color: #28a745; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 1.1em; }
        .add-to-cart-btn:hover { background-color: #218838; }
        .thumbnail-gallery { display: flex; justify-content: center; gap: 10px; margin-top: 10px; }
        .thumbnail-gallery img { width: 60px; height: 60px; object-fit: cover; border: 1px solid #ddd; border-radius: 3px; cursor: pointer; }
        .thumbnail-gallery img.active { border-color: #007bff; border-width: 2px; }
        
        /* Review Section Styles */
        .review-section { margin-top: 40px; border-top: 1px solid #eee; padding-top: 20px; }
        .review-section h2 { color: #333; margin-bottom: 20px; }
        .review-form { background-color: #f9f9f9; padding: 20px; border-radius: 8px; margin-bottom: 30px; }
        .review-form .form-group { margin-bottom: 15px; }
        .review-form .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .review-form .form-group input[type="text"], .review-form .form-group textarea, .review-form .form-group select {
            width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .review-form .form-group textarea { min-height: 80px; resize: vertical; }
        .review-form .form-group button { padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .review-form .form-group button:hover { background-color: #0056b3; }
        .star-rating input[type="radio"] { display: none; }
        .star-rating label { font-size: 1.5em; color: #ccc; cursor: pointer; }
        .star-rating label:hover, .star-rating label:hover ~ label,
        .star-rating input[type="radio"]:checked ~ label { color: #ffc107; }
        .review-item { border: 1px solid #eee; padding: 15px; border-radius: 8px; margin-bottom: 15px; background-color: #fff; }
        .review-item .review-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
        .review-item .reviewer-info { font-weight: bold; color: #555; }
        .review-item .review-date { font-size: 0.9em; color: #777; }
        .review-item .review-rating { color: #ffc107; font-size: 1.2em; }
        .review-item .review-title { font-weight: bold; margin-bottom: 5px; }
        .review-item .review-text { color: #333; line-height: 1.6; }
        .message-success { color: green; text-align: center; margin-bottom: 15px; }
        .message-error { color: red; text-align: center; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="container">
        <c:if test="${not empty requestScope.product}">
            <h1>${requestScope.product.productName}</h1>
            <div class="product-detail">
                <div class="product-image">
                    <c:set var="mainImage" value="${null}"/>
                    <c:forEach var="img" items="${requestScope.product.images}">
                        <c:if test="${img.mainImage}">
                            <c:set var="mainImage" value="${img}"/>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${not empty mainImage}">
                            <img id="mainProductImage" src="${pageContext.request.contextPath}/static/images/${mainImage.imageURL.substring(mainImage.imageURL.lastIndexOf('/') + 1)}" alt="${requestScope.product.productName}">
                        </c:when>
                        <c:otherwise>
                            <img id="mainProductImage" src="https://via.placeholder.com/300x300?text=No+Image" alt="${requestScope.product.productName}">
                        </c:otherwise>
                    </c:choose>

                    <div class="thumbnail-gallery">
                        <c:forEach var="img" items="${requestScope.product.images}">
                            <img src="${pageContext.request.contextPath}/static/images/${img.imageURL.substring(img.imageURL.lastIndexOf('/') + 1)}" alt="${img.altText}" 
                                 onclick="changeMainImage(this)" class="${img.mainImage ? 'active' : ''}">
                        </c:forEach>
                    </div>
                </div>
                <div class="product-info">
                    <h2>${requestScope.product.productName}</h2>
                    <p class="price">
                        <fmt:setLocale value="vi_VN"/>
                        <fmt:formatNumber value="${requestScope.product.price}" type="currency"/>
                    </p>
                    <p><span class="label">Mô tả ngắn:</span> ${requestScope.product.shortDescription}</p>
                    <p><span class="label">Mô tả chi tiết:</span> ${requestScope.product.description}</p>
                    <p><span class="label">SKU:</span> ${requestScope.product.sku}</p>
                    <p><span class="label">Danh mục:</span> 
                        <c:choose>
                            <c:when test="${not empty requestScope.product.category}">
                                ${requestScope.product.category.categoryName}
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><span class="label">Thương hiệu:</span> 
                        <c:choose>
                            <c:when test="${not empty requestScope.product.brand}">
                                ${requestScope.product.brand.brandName}
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><span class="label">Số lượng tồn kho:</span> ${requestScope.product.stockQuantity}</p>
                    <p><span class="label">Đánh giá trung bình:</span> ${requestScope.product.averageRating} (${requestScope.product.reviewCount} đánh giá)</p>
                    <p><span class="label">Kích thước:</span> ${requestScope.product.dimensions}</p>
                    <p><span class="label">Cân nặng:</span> ${requestScope.product.weight} kg</p>
                    <p><span class="label">Ngày tạo:</span> <fmt:formatDate value="${requestScope.product.createdDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                    <p><span class="label">Ngày cập nhật:</span> <fmt:formatDate value="${requestScope.product.modifiedDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                    
                    <form action="${pageContext.request.contextPath}/cart/add" method="post" class="add-to-cart-form">
                        <input type="hidden" name="productId" value="${requestScope.product.productID}">
                        <label for="quantity">Số lượng:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1" class="quantity-input">
                        <button type="submit" class="add-to-cart-btn">Thêm vào giỏ hàng</button>
                    </form>
                </div>
            </div>

            <div class="review-section">
                <h2>Đánh giá Sản phẩm</h2>

                <c:if test="${not empty param.message}">
                    <p class="message-success">${param.message}</p>
                </c:if>
                <c:if test="${not empty requestScope.errorMessage}">
                    <p class="message-error">${requestScope.errorMessage}</p>
                </c:if>

                <c:if test="${not empty sessionScope.user}">
                    <div class="review-form">
                        <h3>Gửi đánh giá của bạn</h3>
                        <form action="${pageContext.request.contextPath}/product/review/add" method="post">
                            <input type="hidden" name="productId" value="${requestScope.product.productID}">
                            <div class="form-group">
                                <label>Điểm đánh giá:</label>
                                <div class="star-rating">
                                    <input type="radio" id="star5" name="rating" value="5" required><label for="star5" title="5 sao">&#9733;</label>
                                    <input type="radio" id="star4" name="rating" value="4"><label for="star4" title="4 sao">&#9733;</label>
                                    <input type="radio" id="star3" name="rating" value="3"><label for="star3" title="3 sao">&#9733;</label>
                                    <input type="radio" id="star2" name="rating" value="2"><label for="star2" title="2 sao">&#9733;</label>
                                    <input type="radio" id="star1" name="rating" value="1"><label for="star1" title="1 sao">&#9733;</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="reviewTitle">Tiêu đề:</label>
                                <input type="text" id="reviewTitle" name="title" placeholder="Tóm tắt đánh giá của bạn">
                            </div>
                            <div class="form-group">
                                <label for="reviewText">Nội dung đánh giá:</label>
                                <textarea id="reviewText" name="reviewText" rows="5" placeholder="Viết đánh giá chi tiết của bạn..."></textarea>
                            </div>
                            <div class="form-group">
                                <button type="submit">Gửi đánh giá</button>
                            </div>
                        </form>
                    </div>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    <p style="text-align: center;">Vui lòng <a href="${pageContext.request.contextPath}/login">đăng nhập</a> để gửi đánh giá.</p>
                </c:if>

                <h3>Các đánh giá khác (${requestScope.product.reviewCount})</h3>
                <c:if test="${empty requestScope.reviews}">
                    <p style="text-align: center;">Chưa có đánh giá nào cho sản phẩm này.</p>
                </c:if>
                <c:forEach var="review" items="${requestScope.reviews}">
                    <div class="review-item">
                        <div class="review-header">
                            <span class="reviewer-info">${review.userFullName} (<c:out value="${review.username}"/>)</span>
                            <span class="review-date"><fmt:formatDate value="${review.createdDate}" pattern="dd/MM/yyyy"/></span>
                        </div>
                        <div class="review-rating">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= review.rating}">&#9733;</c:when>
                                    <c:otherwise>&#9734;</c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <p class="review-title">${review.title}</p>
                        <p class="review-text">${review.reviewText}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty requestScope.product}">
            <p style="text-align: center; color: red;">Không tìm thấy thông tin sản phẩm.</p>
        </c:if>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/products">Quay lại danh sách sản phẩm</a>
        </div>
    </div>

    <script>
        function changeMainImage(thumbnail) {
            var mainImage = document.getElementById('mainProductImage');
            mainImage.src = thumbnail.src;

            // Remove active class from all thumbnails
            var thumbnails = document.querySelectorAll('.thumbnail-gallery img');
            thumbnails.forEach(function(img) {
                img.classList.remove('active');
            });

            // Add active class to the clicked thumbnail
            thumbnail.classList.add('active');
        }
    </script>
</body>
</html>