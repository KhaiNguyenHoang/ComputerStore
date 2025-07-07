<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Hình ảnh Sản phẩm - Admin</title>
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
        .message { color: green; text-align: center; margin-bottom: 15px; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; }
        .image-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(150px, 1fr)); gap: 20px; margin-top: 20px; }
        .image-card { border: 1px solid #ddd; border-radius: 8px; padding: 10px; text-align: center; background-color: #fff; box-shadow: 0 2px 5px rgba(0,0,0,0.05); position: relative; }
        .image-card img { max-width: 100%; height: 120px; object-fit: contain; margin-bottom: 10px; }
        .image-card .actions { margin-top: 10px; }
        .image-card .actions a, .image-card .actions button { padding: 5px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.8em; text-decoration: none; margin: 0 2px; }
        .image-card .actions .delete-btn { background-color: #dc3545; color: white; }
        .image-card .actions .delete-btn:hover { background-color: #c82333; }
        .upload-form { background-color: #e9ecef; padding: 20px; border-radius: 8px; margin-top: 30px; }
        .upload-form .form-group { margin-bottom: 15px; }
        .upload-form .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .upload-form .form-group input[type="text"], .upload-form .form-group input[type="number"], .upload-form .form-group input[type="file"] {
            width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .upload-form .form-group input[type="checkbox"] { margin-right: 5px; }
        .upload-form .form-group button { padding: 10px 20px; background-color: #28a745; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .upload-form .form-group button:hover { background-color: #218838; }
        .back-to-product { display: block; text-align: center; margin-top: 30px; }
        .back-to-product a { color: #007bff; text-decoration: none; font-weight: bold; }
        .back-to-product a:hover { text-decoration: underline; }
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
            <h1>Quản lý Hình ảnh cho Sản phẩm ID: ${requestScope.productId}</h1>
        </div>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <h2 style="color: #333;">Hình ảnh hiện có</h2>
        <c:if test="${empty requestScope.images}">
            <p style="text-align: center;">Chưa có hình ảnh nào cho sản phẩm này.</p>
        </c:if>
        <div class="image-grid">
            <c:forEach var="image" items="${requestScope.images}">
                <div class="image-card">
                    <img src="${pageContext.request.contextPath}/static/images/${image.imageURL.substring(image.imageURL.lastIndexOf('/') + 1)}" alt="${image.altText}">
                    <p>Alt Text: ${image.altText}</p>
                    <p>Display Order: ${image.displayOrder}</p>
                    <p>Main Image: <c:choose><c:when test="${image.mainImage}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></p>
                    <div class="actions">
                        <a href="${pageContext.request.contextPath}/admin/product-images/delete?id=${image.imageID}&productId=${requestScope.productId}" class="delete-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa hình ảnh này không?');">Xóa</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <h2 style="color: #333; margin-top: 40px;">Tải lên hình ảnh mới</h2>
        <div class="upload-form">
            <form action="${pageContext.request.contextPath}/admin/product-images/upload" method="post" enctype="multipart/form-data">
                <input type="hidden" name="productId" value="${requestScope.productId}">
                <div class="form-group">
                    <label for="imageFile">Chọn tệp hình ảnh:</label>
                    <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
                </div>
                <div class="form-group">
                    <label for="altText">Văn bản thay thế (Alt Text):</label>
                    <input type="text" id="altText" name="altText" placeholder="Mô tả hình ảnh">
                </div>
                <div class="form-group">
                    <label for="displayOrder">Thứ tự hiển thị:</label>
                    <input type="number" id="displayOrder" name="displayOrder" value="0">
                </div>
                <div class="form-group">
                    <input type="checkbox" id="isMainImage" name="isMainImage">
                    <label for="isMainImage">Đặt làm hình ảnh chính</label>
                </div>
                <div class="form-group">
                    <button type="submit">Tải lên hình ảnh</button>
                </div>
            </form>
        </div>

        <div class="back-to-product">
            <a href="${pageContext.request.contextPath}/admin/products/edit?id=${requestScope.productId}">Quay lại trang sửa sản phẩm</a>
        </div>
    </div>
</body>
</html>
