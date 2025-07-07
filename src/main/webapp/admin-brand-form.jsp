<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title><c:choose><c:when test="${not empty requestScope.brand}">Sửa Thương hiệu</c:when><c:otherwise>Thêm Thương hiệu mới</c:otherwise></c:choose> - Admin</title>
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
        .form-group input[type="text"], .form-group textarea {
            width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group textarea { min-height: 80px; resize: vertical; }
        .form-group input[type="checkbox"] { margin-right: 5px; }
        .form-group button { padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .back-link { display: block; text-align: center; margin-top: 20px; }
        .back-link a { color: #007bff; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
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
            <h1><c:choose><c:when test="${not empty requestScope.brand}">Sửa Thương hiệu</c:when><c:otherwise>Thêm Thương hiệu mới</c:otherwise></c:choose></h1>
        </div>

        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/brands/<c:choose><c:when test="${not empty requestScope.brand}">edit</c:when><c:otherwise>add</c:otherwise></c:choose>" method="post">
            <c:if test="${not empty requestScope.brand}">
                <input type="hidden" name="brandId" value="${requestScope.brand.brandID}">
            </c:if>

            <div class="form-group">
                <label for="brandName">Tên Thương hiệu:</label>
                <input type="text" id="brandName" name="brandName" value="${requestScope.brand.brandName}" required>
            </div>
            <div class="form-group">
                <label for="description">Mô tả:</label>
                <textarea id="description" name="description">${requestScope.brand.description}</textarea>
            </div>
            <div class="form-group">
                <label for="logoURL">URL Logo:</label>
                <input type="text" id="logoURL" name="logoURL" value="${requestScope.brand.logoURL}">
            </div>
            <div class="form-group">
                <label for="website">Website:</label>
                <input type="text" id="website" name="website" value="${requestScope.brand.website}">
            </div>
            <div class="form-group">
                <input type="checkbox" id="isActive" name="isActive" <c:if test="${requestScope.brand.active}">checked</c:if>>
                <label for="isActive">Hoạt động</label>
            </div>

            <div class="form-group">
                <button type="submit"><c:choose><c:when test="${not empty requestScope.brand}">Cập nhật Thương hiệu</c:when><c:otherwise>Thêm Thương hiệu</c:otherwise></c:choose></button>
            </div>
        </form>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/admin/brands">Quay lại danh sách thương hiệu</a>
        </div>
    </div>
</body>
</html>
