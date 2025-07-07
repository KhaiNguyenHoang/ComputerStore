<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Mã giảm giá - Admin</title>
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
        .add-button { display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px; margin-bottom: 20px; }
        .add-button:hover { background-color: #218838; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-buttons a, .action-buttons button { padding: 5px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; text-decoration: none; margin-right: 5px; }
        .action-buttons .edit-btn { background-color: #ffc107; color: #333; }
        .action-buttons .edit-btn:hover { background-color: #e0a800; }
        .action-buttons .delete-btn { background-color: #dc3545; color: white; }
        .action-buttons .delete-btn:hover { background-color: #c82333; }
        .status-active { color: green; font-weight: bold; }
        .status-inactive { color: red; font-weight: bold; }
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
            <h1>Quản lý Mã giảm giá</h1>
        </div>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin/coupons/add" class="add-button">Thêm Mã giảm giá mới</a>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Mã Code</th>
                    <th>Tên</th>
                    <th>Loại giảm giá</th>
                    <th>Giá trị</th>
                    <th>Đơn tối thiểu</th>
                    <th>Giảm tối đa</th>
                    <th>Giới hạn sử dụng</th>
                    <th>Đã sử dụng</th>
                    <th>Hoạt động</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="coupon" items="${requestScope.couponList}">
                    <tr>
                        <td>${coupon.couponID}</td>
                        <td>${coupon.couponCode}</td>
                        <td>${coupon.couponName}</td>
                        <td>${coupon.discountType}</td>
                        <td>
                            <c:choose>
                                <c:when test="${coupon.discountType == 'percentage'}">
                                    <fmt:formatNumber value="${coupon.discountValue}" type="percent"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber value="${coupon.discountValue}" type="currency" currencySymbol="₫"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatNumber value="${coupon.minOrderAmount}" type="currency" currencySymbol="₫"/></td>
                        <td><fmt:formatNumber value="${coupon.maxDiscountAmount}" type="currency" currencySymbol="₫"/></td>
                        <td>${coupon.usageLimit != null ? coupon.usageLimit : 'Không giới hạn'}</td>
                        <td>${coupon.usedCount}</td>
                        <td>
                            <c:choose>
                                <c:when test="${coupon.active}"><span class="status-active">Có</span></c:when>
                                <c:otherwise><span class="status-inactive">Không</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${coupon.startDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td><fmt:formatDate value="${coupon.endDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/coupons/edit?id=${coupon.couponID}" class="edit-btn">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/coupons/delete?id=${coupon.couponID}" class="delete-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa mã giảm giá này không?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty requestScope.couponList}">
                    <tr>
                        <td colspan="13">Không có mã giảm giá nào để hiển thị.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
