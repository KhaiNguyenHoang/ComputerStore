<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Giao dịch Tồn kho - Admin</title>
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
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .transaction-type.in { color: green; font-weight: bold; }
        .transaction-type.out { color: red; font-weight: bold; }
        .transaction-type.adjustment { color: orange; font-weight: bold; }
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
            <li><a href="${pageContext.request.contextPath}/admin/inventory">Quản lý Tồn kho</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
        </ul>
    </div>

    <div class="main-content">
        <div class="header-main">
            <h1>Quản lý Giao dịch Tồn kho</h1>
        </div>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>ID Giao dịch</th>
                    <th>Sản phẩm</th>
                    <th>Loại</th>
                    <th>Số lượng</th>
                    <th>Tham chiếu</th>
                    <th>Ghi chú</th>
                    <th>Ngày tạo</th>
                    <th>Người tạo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="transaction" items="${requestScope.transactions}">
                    <tr>
                        <td>${transaction.transactionID}</td>
                        <td>${transaction.product.productName} (${transaction.productID})</td>
                        <td>
                            <span class="transaction-type 
                                <c:choose>
                                    <c:when test="${transaction.transactionType == 'in'}">in</c:when>
                                    <c:when test="${transaction.transactionType == 'out'}">out</c:when>
                                    <c:when test="${transaction.transactionType == 'adjustment'}">adjustment</c:when>
                                </c:choose>
                            ">${transaction.transactionType}</span>
                        </td>
                        <td>${transaction.quantity}</td>
                        <td>${transaction.referenceType} - ${transaction.referenceID}</td>
                        <td>${transaction.notes}</td>
                        <td><fmt:formatDate value="${transaction.createdDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                        <td>${transaction.createdByUser.username} (${transaction.createdBy})</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty requestScope.transactions}">
                    <tr>
                        <td colspan="8">Không có giao dịch tồn kho nào để hiển thị.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
