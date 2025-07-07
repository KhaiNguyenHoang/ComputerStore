<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử Đơn hàng của bạn</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 960px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .message { color: green; text-align: center; margin-bottom: 15px; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; }
        .order-list { display: grid; grid-template-columns: 1fr; gap: 20px; }
        .order-card { border: 1px solid #ddd; border-radius: 8px; padding: 15px; background-color: #f9f9f9; }
        .order-card h3 { margin-top: 0; color: #007bff; }
        .order-card p { margin: 5px 0; }
        .order-card .status { font-weight: bold; }
        .order-card .status.pending { color: #ffc107; }
        .order-card .status.processing { color: #17a2b8; }
        .order-card .status.shipped { color: #007bff; }
        .order-card .status.delivered { color: #28a745; }
        .order-card .status.cancelled { color: #dc3545; }
        .order-card .view-detail-btn { display: inline-block; padding: 8px 12px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; margin-top: 10px; }
        .order-card .view-detail-btn:hover { background-color: #5a6268; }
        .no-orders { text-align: center; color: #555; font-size: 1.2em; margin-top: 50px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>ComputerStore</h1>
    </div>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản Phẩm</a>
        <a href="${pageContext.request.contextPath}/cart">Giỏ Hàng</a>
        <a href="${pageContext.request.contextPath}/orders">Đơn Hàng</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
    </div>

    <div class="container">
        <h1>Lịch sử Đơn hàng của bạn</h1>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <c:if test="${empty requestScope.orders}">
            <p class="no-orders">Bạn chưa có đơn hàng nào.</p>
        </c:if>

        <div class="order-list">
            <c:forEach var="order" items="${requestScope.orders}">
                <div class="order-card">
                    <h3>Đơn hàng #${order.orderNumber}</h3>
                    <p>Ngày đặt: <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                    <p>Tổng cộng: 
                        <fmt:setLocale value="vi_VN"/>
                        <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/>
                    </p>
                    <p>Trạng thái đơn hàng: 
                        <span class="status 
                            <c:choose>
                                <c:when test="${order.orderStatus == 'Pending'}">pending</c:when>
                                <c:when test="${order.orderStatus == 'Processing'}">processing</c:when>
                                <c:when test="${order.orderStatus == 'Shipped'}">shipped</c:when>
                                <c:when test="${order.orderStatus == 'Delivered'}">delivered</c:when>
                                <c:when test="${order.orderStatus == 'Cancelled'}">cancelled</c:when>
                            </c:choose>
                        ">${order.orderStatus}</span>
                    </p>
                    <p>Trạng thái thanh toán: <span class="status 
                            <c:choose>
                                <c:when test="${order.paymentStatus == 'Pending'}">pending</c:when>
                                <c:when test="${order.paymentStatus == 'Paid'}">delivered</c:when>
                                <c:when test="${order.paymentStatus == 'Refunded'}">cancelled</c:when>
                            </c:choose>
                        ">${order.paymentStatus}</span></p>
                    <a href="${pageContext.request.contextPath}/orders/detail?id=${order.orderID}" class="view-detail-btn">Xem chi tiết</a>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
