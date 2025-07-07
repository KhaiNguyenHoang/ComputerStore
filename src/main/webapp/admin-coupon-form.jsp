<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title><c:choose><c:when test="${not empty requestScope.coupon}">Sửa Mã giảm giá</c:when><c:otherwise>Thêm Mã giảm giá mới</c:otherwise></c:choose> - Admin</title>
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
        .form-group input[type="text"], .form-group input[type="number"], .form-group textarea, .form-group select, .form-group input[type="datetime-local"] {
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
            <li><a href="${pageContext.request.contextPath}/admin/reviews">Quản lý Đánh giá</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/coupons">Quản lý Mã giảm giá</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
        </ul>
    </div>

    <div class="main-content">
        <div class="header-main">
            <h1><c:choose><c:when test="${not empty requestScope.coupon}">Sửa Mã giảm giá</c:when><c:otherwise>Thêm Mã giảm giá mới</c:otherwise></c:choose></h1>
        </div>

        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/coupons/<c:choose><c:when test="${not empty requestScope.coupon}">edit</c:when><c:otherwise>add</c:otherwise></c:choose>" method="post">
            <c:if test="${not empty requestScope.coupon}">
                <input type="hidden" name="couponId" value="${requestScope.coupon.couponID}">
            </c:if>

            <div class="form-group">
                <label for="couponCode">Mã Code:</label>
                <input type="text" id="couponCode" name="couponCode" value="${requestScope.coupon.couponCode}" required>
            </div>
            <div class="form-group">
                <label for="couponName">Tên Mã giảm giá:</label>
                <input type="text" id="couponName" name="couponName" value="${requestScope.coupon.couponName}">
            </div>
            <div class="form-group">
                <label for="description">Mô tả:</label>
                <textarea id="description" name="description">${requestScope.coupon.description}</textarea>
            </div>
            <div class="form-group">
                <label for="discountType">Loại giảm giá:</label>
                <select id="discountType" name="discountType" required>
                    <option value="percentage" <c:if test="${requestScope.coupon.discountType == 'percentage'}">selected</c:if>>Phần trăm</option>
                    <option value="fixed_amount" <c:if test="${requestScope.coupon.discountType == 'fixed_amount'}">selected</c:if>>Số tiền cố định</option>
                </select>
            </div>
            <div class="form-group">
                <label for="discountValue">Giá trị giảm giá:</label>
                <input type="number" id="discountValue" name="discountValue" step="0.01" value="${requestScope.coupon.discountValue}" required>
            </div>
            <div class="form-group">
                <label for="minOrderAmount">Đơn hàng tối thiểu:</label>
                <input type="number" id="minOrderAmount" name="minOrderAmount" step="0.01" value="${requestScope.coupon.minOrderAmount}">
            </div>
            <div class="form-group">
                <label for="maxDiscountAmount">Giảm giá tối đa:</label>
                <input type="number" id="maxDiscountAmount" name="maxDiscountAmount" step="0.01" value="${requestScope.coupon.maxDiscountAmount}">
            </div>
            <div class="form-group">
                <label for="usageLimit">Giới hạn sử dụng (tổng số lần):</label>
                <input type="number" id="usageLimit" name="usageLimit" value="${requestScope.coupon.usageLimit}">
            </div>
            <div class="form-group">
                <input type="checkbox" id="isActive" name="isActive" <c:if test="${requestScope.coupon.active}">checked</c:if>>
                <label for="isActive">Hoạt động</label>
            </div>
            <div class="form-group">
                <label for="startDate">Ngày bắt đầu:</label>
                <input type="datetime-local" id="startDate" name="startDate" value="<fmt:formatDate value='${requestScope.coupon.startDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" required>
            </div>
            <div class="form-group">
                <label for="endDate">Ngày kết thúc:</label>
                <input type="datetime-local" id="endDate" name="endDate" value="<fmt:formatDate value='${requestScope.coupon.endDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" required>
            </div>

            <div class="form-group">
                <button type="submit"><c:choose><c:when test="${not empty requestScope.coupon}">Cập nhật Mã giảm giá</c:when><c:otherwise>Thêm Mã giảm giá</c:otherwise></c:choose></button>
            </div>
        </form>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/admin/coupons">Quay lại danh sách mã giảm giá</a>
        </div>
    </div>
</body>
</html>