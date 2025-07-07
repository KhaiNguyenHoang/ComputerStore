<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Giỏ Hàng Của Bạn</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 960px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        .product-image { width: 80px; height: 80px; object-fit: contain; margin-right: 10px; vertical-align: middle; }
        .product-name { font-weight: bold; }
        .quantity-input { width: 60px; padding: 5px; border: 1px solid #ddd; border-radius: 4px; text-align: center; }
        .update-btn, .remove-btn, .clear-cart-btn, .checkout-btn { padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; }
        .update-btn { background-color: #007bff; color: white; }
        .update-btn:hover { background-color: #0056b3; }
        .remove-btn { background-color: #dc3545; color: white; margin-left: 5px; }
        .remove-btn:hover { background-color: #c82333; }
        .cart-summary { text-align: right; font-size: 1.2em; margin-top: 20px; }
        .cart-actions { text-align: right; margin-top: 20px; }
        .clear-cart-btn { background-color: #ffc107; color: #333; margin-right: 10px; }
        .clear-cart-btn:hover { background-color: #e0a800; }
        .checkout-btn { background-color: #28a745; color: white; }
        .checkout-btn:hover { background-color: #218838; }
        .empty-cart { text-align: center; color: #555; font-size: 1.2em; margin-top: 50px; }
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
        <h1>Giỏ Hàng Của Bạn</h1>

        <c:if test="${empty requestScope.cartItems}">
            <p class="empty-cart">Giỏ hàng của bạn đang trống. <a href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>.</p>
        </c:if>

        <c:if test="${not empty requestScope.cartItems}">
            <table>
                <thead>
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Tổng cộng</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="cartTotal" value="0" />
                    <c:forEach var="item" items="${requestScope.cartItems}">
                        <c:set var="itemTotalPrice" value="${item.product.price * item.quantity}" />
                        <c:set var="cartTotal" value="${cartTotal + itemTotalPrice}" />
                        <tr>
                            <td>
                                <img src="https://via.placeholder.com/80x80?text=Product" alt="${item.product.productName}" class="product-image">
                                <span class="product-name">${item.product.productName}</span>
                            </td>
                            <td>
                                <fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${item.product.price}" type="currency"/>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart/update" method="post" style="display: inline;">
                                    <input type="hidden" name="productId" value="${item.product.productID}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" class="quantity-input">
                                    <button type="submit" class="update-btn">Cập nhật</button>
                                </form>
                            </td>
                            <td>
                                <fmt:setLocale value="vi_VN"/>
                                <fmt:formatNumber value="${itemTotalPrice}" type="currency"/>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart/remove" method="get" style="display: inline;">
                                    <input type="hidden" name="productId" value="${item.product.productID}">
                                    <button type="submit" class="remove-btn">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="cart-summary">
                <strong>Tổng cộng giỏ hàng:</strong> 
                <fmt:setLocale value="vi_VN"/>
                <fmt:formatNumber value="${cartTotal}" type="currency"/>
            </div>

            <div class="cart-actions">
                <form action="${pageContext.request.contextPath}/cart/clear" method="get" style="display: inline;">
                    <button type="submit" class="clear-cart-btn">Xóa toàn bộ giỏ hàng</button>
                </form>
                <a href="${pageContext.request.contextPath}/checkout" class="checkout-btn">Tiến hành thanh toán</a>
            </div>
        </c:if>
    </div>
</body>
</html>