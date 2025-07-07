<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><c:choose><c:when test="${not empty requestScope.product}">Sửa Sản phẩm</c:when><c:otherwise>Thêm Sản phẩm mới</c:otherwise></c:choose> - Admin</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f4f4; display: flex; }
        .sidebar { width: 200px; background-color: #333; color: white; padding: 20px; height: 100vh; position: fixed; }
        .sidebar h2 { text-align: center; margin-bottom: 30px; }
        .sidebar ul { list-style: none; padding: 0; }
        .sidebar ul li { margin-bottom: 15px; }
        .sidebar ul li a { color: white; text-decoration: none; display: block; padding: 10px; border-radius: 5px; }
        .sidebar ul li a:hover { background-color: #555; }
        .main-content { margin-left: 220px; padding: 20px; width: calc(100% - 220px); }
        .header-main { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; margin-bottom: 20px; border-radius: 8px; }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .message { color: red; text-align: center; margin-bottom: 15px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        .form-group input[type="text"], .form-group input[type="number"], .form-group textarea, .form-group select {
            width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group textarea { min-height: 80px; resize: vertical; }
        .form-group input[type="checkbox"] { margin-right: 5px; }
        .form-group button { padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .back-link { display: block; text-align: center; margin-top: 20px; }
        .back-link a { color: #007bff; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
        .manage-images-link { display: block; text-align: center; margin-top: 20px; }
        .manage-images-link a { display: inline-block; padding: 10px 20px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
        .manage-images-link a:hover { background-color: #5a6268; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Admin Panel</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Quản lý Sản phẩm</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Quản lý Danh mục</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/brands">Quản lý Thương hiệu</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Quản lý Người dùng</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
        </ul>
    </div>

    <div class="main-content">
        <div class="header-main">
            <h1><c:choose><c:when test="${not empty requestScope.product}">Sửa Sản phẩm</c:when><c:otherwise>Thêm Sản phẩm mới</c:otherwise></c:choose></h1>
        </div>

        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/products/<c:choose><c:when test="${not empty requestScope.product}">edit</c:when><c:otherwise>add</c:otherwise></c:choose>" method="post">
            <c:if test="${not empty requestScope.product}">
                <input type="hidden" name="productId" value="${requestScope.product.productID}">
            </c:if>

            <div class="form-group">
                <label for="productName">Tên Sản phẩm:</label>
                <input type="text" id="productName" name="productName" value="${requestScope.product.productName}" required>
            </div>
            <div class="form-group">
                <label for="sku">SKU:</label>
                <input type="text" id="sku" name="sku" value="${requestScope.product.sku}">
            </div>
            <div class="form-group">
                <label for="categoryId">Danh mục:</label>
                <select id="categoryId" name="categoryId" required>
                    <option value="">-- Chọn Danh mục --</option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category.categoryID}" <c:if test="${category.categoryID == requestScope.product.categoryID}">selected</c:if>>${category.categoryName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="brandId">Thương hiệu:</label>
                <select id="brandId" name="brandId">
                    <option value="">-- Chọn Thương hiệu --</option>
                    <c:forEach var="brand" items="${requestScope.brands}">
                        <option value="${brand.brandID}" <c:if test="${brand.brandID == requestScope.product.brandID}">selected</c:if>>${brand.brandName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="description">Mô tả:</label>
                <textarea id="description" name="description">${requestScope.product.description}</textarea>
            </div>
            <div class="form-group">
                <label for="shortDescription">Mô tả ngắn:</label>
                <input type="text" id="shortDescription" name="shortDescription" value="${requestScope.product.shortDescription}">
            </div>
            <div class="form-group">
                <label for="price">Giá:</label>
                <input type="number" id="price" name="price" step="0.01" value="${requestScope.product.price}" required>
            </div>
            <div class="form-group">
                <label for="comparePrice">Giá so sánh:</label>
                <input type="number" id="comparePrice" name="comparePrice" step="0.01" value="${requestScope.product.comparePrice}">
            </div>
            <div class="form-group">
                <label for="costPrice">Giá vốn:</label>
                <input type="number" id="costPrice" name="costPrice" step="0.01" value="${requestScope.product.costPrice}">
            </div>
            <div class="form-group">
                <label for="weight">Cân nặng (kg):</label>
                <input type="number" id="weight" name="weight" step="0.01" value="${requestScope.product.weight}">
            </div>
            <div class="form-group">
                <label for="dimensions">Kích thước:</label>
                <input type="text" id="dimensions" name="dimensions" value="${requestScope.product.dimensions}">
            </div>
            <div class="form-group">
                <label for="stockQuantity">Số lượng tồn kho:</label>
                <input type="number" id="stockQuantity" name="stockQuantity" value="${requestScope.product.stockQuantity}" required>
            </div>
            <div class="form-group">
                <label for="minStockLevel">Mức tồn kho tối thiểu:</label>
                <input type="number" id="minStockLevel" name="minStockLevel" value="${requestScope.product.minStockLevel}">
            </div>
            <div class="form-group">
                <label for="maxStockLevel">Mức tồn kho tối đa:</label>
                <input type="number" id="maxStockLevel" name="maxStockLevel" value="${requestScope.product.maxStockLevel}">
            </div>
            <div class="form-group">
                <input type="checkbox" id="isActive" name="isActive" <c:if test="${requestScope.product.active}">checked</c:if>>
                <label for="isActive">Hoạt động</label>
            </div>
            <div class="form-group">
                <input type="checkbox" id="isFeatured" name="isFeatured" <c:if test="${requestScope.product.featured}">checked</c:if>>
                <label for="isFeatured">Nổi bật</label>
            </div>
            <div class="form-group">
                <label for="viewCount">Lượt xem:</label>
                <input type="number" id="viewCount" name="viewCount" value="${requestScope.product.viewCount}">
            </div>
            <div class="form-group">
                <label for="salesCount">Lượt bán:</label>
                <input type="number" id="salesCount" name="salesCount" value="${requestScope.product.salesCount}">
            </div>
            <div class="form-group">
                <label for="averageRating">Đánh giá trung bình:</label>
                <input type="number" id="averageRating" name="averageRating" step="0.01" value="${requestScope.product.averageRating}">
            </div>
            <div class="form-group">
                <label for="reviewCount">Số lượng đánh giá:</label>
                <input type="number" id="reviewCount" name="reviewCount" value="${requestScope.product.reviewCount}">
            </div>

            <div class="form-group">
                <button type="submit"><c:choose><c:when test="${not empty requestScope.product}">Cập nhật Sản phẩm</c:when><c:otherwise>Thêm Sản phẩm</c:otherwise></c:choose></button>
            </div>
        </form>
        <c:if test="${not empty requestScope.product}">
            <div class="manage-images-link">
                <a href="${pageContext.request.contextPath}/admin/product-images?productId=${requestScope.product.productID}">Quản lý Hình ảnh Sản phẩm</a>
            </div>
        </c:if>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/admin/products">Quay lại danh sách sản phẩm</a>
        </div>
    </div>
</body>
</html>