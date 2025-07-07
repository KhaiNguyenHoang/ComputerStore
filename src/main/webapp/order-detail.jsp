<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết Đơn hàng #${order.orderNumber}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 960px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .section-title { color: #007bff; border-bottom: 1px solid #eee; padding-bottom: 10px; margin-top: 30px; margin-bottom: 20px; }
        .order-info p { margin: 5px 0; }
        .order-info .label { font-weight: bold; color: #555; }
        .order-items-table { width: 100%; border-collapse: collapse; margin-top: 20px; margin-bottom: 20px; }
        .order-items-table th, .order-items-table td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        .order-items-table th { background-color: #f2f2f2; }
        .total-row { font-weight: bold; }
        .address-box { border: 1px solid #ddd; padding: 15px; border-radius: 5px; margin-top: 10px; background-color: #f9f9f9; }
        .address-box p { margin: 5px 0; }
        .back-link { display: block; text-align: center; margin-top: 30px; }
        .back-link a { color: #007bff; text-decoration: none; font-weight: bold; }
        .back-link a:hover { text-decoration: underline; }
        .status { font-weight: bold; }
        .status.pending { color: #ffc107; }
        .status.processing { color: #17a2b8; }
        .status.shipped { color: #007bff; }
        .status.delivered { color: #28a745; }
        .status.cancelled { color: #dc3545; }
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
        <c:if test="${not empty requestScope.order}">
            <h1>Chi tiết Đơn hàng #${requestScope.order.orderNumber}</h1>

            <c:if test="${not empty param.message}">
                <p class="message" style="color: green; text-align: center;">${param.message}</p>
            </c:if>

            <h2 class="section-title">Thông tin Đơn hàng</h2>
            <div class="order-info">
                <p><span class="label">Mã đơn hàng:</span> ${requestScope.order.orderNumber}</p>
                <p><span class="label">Ngày đặt:</span> <fmt:formatDate value="${requestScope.order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                <p><span class="label">Trạng thái đơn hàng:</span> 
                    <span class="status 
                        <c:choose>
                            <c:when test="${requestScope.order.orderStatus == 'Pending'}">pending</c:when>
                            <c:when test="${requestScope.order.orderStatus == 'Processing'}">processing</c:when>
                            <c:when test="${requestScope.order.orderStatus == 'Shipped'}">shipped</c:when>
                            <c:when test="${requestScope.order.orderStatus == 'Delivered'}">delivered</c:when>
                            <c:when test="${requestScope.order.orderStatus == 'Cancelled'}">cancelled</c:when>
                        </c:choose>
                    ">${requestScope.order.orderStatus}</span>
                </p>
                <p><span class="label">Trạng thái thanh toán:</span> 
                    <span class="status 
                        <c:choose>
                            <c:when test="${requestScope.order.paymentStatus == 'Pending'}">pending</c:when>
                            <c:when test="${requestScope.order.paymentStatus == 'Paid'}">delivered</c:when>
                            <c:when test="${requestScope.order.paymentStatus == 'Refunded'}">cancelled</c:when>
                        </c:choose>
                    ">${requestScope.order.paymentStatus}</span>
                </p>
                <p><span class="label">Phương thức thanh toán:</span> ${requestScope.order.paymentMethod}</p>
                <p><span class="label">ID Giao dịch thanh toán:</span> ${requestScope.order.paymentTransactionID}</p>
                <p><span class="label">Ghi chú:</span> ${requestScope.order.notes}</p>
            </div>

            <h2 class="section-title">Sản phẩm trong đơn hàng</h2>
            <table class="order-items-table">
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>SKU</th>
                        <th>Số lượng</th>
                        <th>Giá đơn vị</th>
                        <th>Tổng cộng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${requestScope.order.orderItems}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.sku}</td>
                            <td>${item.quantity}</td>
                            <td>
                                <fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${item.unitPrice}" type="currency" currencySymbol="₫"/>
                            </td>
                            <td>
                                <fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="₫"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="total-row">
                        <td colspan="4">Tổng phụ:</td>
                        <td>
                            <fmt:setLocale value="vi_VN"/>
                            <fmt:formatNumber value="${requestScope.order.subtotalAmount}" type="currency" currencySymbol="₫"/>
                        </td>
                    </tr>
                    <tr class="total-row">
                        <td colspan="4">Giảm giá:</td>
                        <td>
                            <fmt:setLocale value="vi_VN"/>
                            <fmt:formatNumber value="${requestScope.order.discountAmount}" type="currency" currencySymbol="₫"/>
                        </td>
                    </tr>
                    <tr class="total-row">
                        <td colspan="4">Tổng cộng:</td>
                        <td>
                            <fmt:setLocale value="vi_VN"/>
                            <fmt:formatNumber value="${requestScope.order.totalAmount}" type="currency" currencySymbol="₫"/>
                        </td>
                    </tr>
                </tbody>
            </table>

            <h2 class="section-title">Địa chỉ giao hàng</h2>
            <div class="address-box">
                <c:if test="${not empty requestScope.order.shippingAddress}">
                    <p><span class="label">Loại địa chỉ:</span> ${requestScope.order.shippingAddress.addressType}</p>
                    <p><span class="label">Địa chỉ:</span> ${requestScope.order.shippingAddress.addressLine1}, ${requestScope.order.shippingAddress.addressLine2}</p>
                    <p><span class="label">Thành phố:</span> ${requestScope.order.shippingAddress.city}</p>
                    <p><span class="label">Tỉnh/Bang:</span> ${requestScope.order.shippingAddress.state}</p>
                    <p><span class="label">Mã bưu chính:</span> ${requestScope.order.shippingAddress.postalCode}</p>
                    <p><span class="label">Quốc gia:</span> ${requestScope.order.shippingAddress.country}</p>
                </c:if>
                <c:if test="${empty requestScope.order.shippingAddress}">
                    <p>Không có thông tin địa chỉ giao hàng.</p>
                </c:if>
            </div>

            <h2 class="section-title">Địa chỉ thanh toán</h2>
            <div class="address-box">
                <c:if test="${not empty requestScope.order.billingAddress}">
                    <p><span class="label">Loại địa chỉ:</span> ${requestScope.order.billingAddress.addressType}</p>
                    <p><span class="label">Địa chỉ:</span> ${requestScope.order.billingAddress.addressLine1}, ${requestScope.order.billingAddress.addressLine2}</p>
                    <p><span class="label">Thành phố:</span> ${requestScope.order.billingAddress.city}</p>
                    <p><span class="label">Tỉnh/Bang:</span> ${requestScope.order.billingAddress.state}</p>
                    <p><span class="label">Mã bưu chính:</span> ${requestScope.order.billingAddress.postalCode}</p>
                    <p><span class="label">Quốc gia:</span> ${requestScope.order.billingAddress.country}</p>
                </c:if>
                <c:if test="${empty requestScope.order.billingAddress}">
                    <p>Không có thông tin địa chỉ thanh toán.</p>
                </c:if>
            </div>

        </c:if>
        <c:if test="${empty requestScope.order}">
            <p style="text-align: center; color: red;">Không tìm thấy đơn hàng.</p>
        </c:if>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/orders">Quay lại lịch sử đơn hàng</a>
        </div>
    </div>
</body>
</html>