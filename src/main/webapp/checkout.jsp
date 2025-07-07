<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Thanh Toán</title>
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
        .cart-summary-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        .cart-summary-table th, .cart-summary-table td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        .cart-summary-table th { background-color: #f2f2f2; }
        .total-row { font-weight: bold; }
        .address-selection { margin-bottom: 20px; }
        .address-selection label { display: block; margin-bottom: 10px; font-weight: bold; }
        .address-item { border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; border-radius: 5px; }
        .address-item input[type="radio"] { margin-right: 10px; }
        .payment-method { margin-bottom: 20px; }
        .payment-method label { display: block; margin-bottom: 10px; font-weight: bold; }
        .payment-method input[type="radio"] { margin-right: 10px; }
        .notes-section label { display: block; margin-bottom: 5px; font-weight: bold; }
        .notes-section textarea { width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; min-height: 80px; }
        .place-order-btn { display: block; width: 100%; padding: 15px; background-color: #28a745; color: white; border: none; border-radius: 5px; font-size: 1.2em; cursor: pointer; margin-top: 30px; }
        .place-order-btn:hover { background-color: #218838; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; }
        .add-address-link { display: block; text-align: center; margin-top: 15px; }
        .add-address-link a { color: #007bff; text-decoration: none; }
        .add-address-link a:hover { text-decoration: underline; }
        .coupon-section { margin-top: 30px; padding: 15px; background-color: #e9ecef; border-radius: 8px; }
        .coupon-section input[type="text"] { padding: 8px; border: 1px solid #ced4da; border-radius: 4px; margin-right: 10px; }
        .coupon-section button { padding: 8px 15px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .coupon-section button:hover { background-color: #0056b3; }
        .coupon-message { margin-top: 10px; font-weight: bold; }
        .coupon-message.success { color: green; }
        .coupon-message.error { color: red; }
        .payment-details-section { margin-top: 30px; padding: 15px; background-color: #f9f9f9; border-radius: 8px; }
        .payment-details-section input[type="text"] { width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        .payment-details-section .form-row { display: flex; gap: 15px; margin-bottom: 15px; }
        .payment-details-section .form-row > div { flex: 1; }
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
        <h1>Thanh Toán</h1>

        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <h2 class="section-title">Tóm tắt đơn hàng</h2>
        <table class="cart-summary-table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Giá đơn vị</th>
                    <th>Tổng cộng</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${requestScope.cartItems}">
                    <tr>
                        <td>${item.product.productName}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₫"/></td>
                        <td><fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencySymbol="₫"/></td>
                    </tr>
                </c:forEach>
                <tr class="total-row">
                    <td colspan="3">Tổng phụ:</td>
                    <td><fmt:formatNumber value="${requestScope.subtotal}" type="currency" currencySymbol="₫"/></td>
                </tr>
                <c:if test="${not empty requestScope.discountAmount and requestScope.discountAmount gt 0}">
                    <tr class="total-row">
                        <td colspan="3">Giảm giá (<c:out value="${requestScope.appliedCouponCode}"/>):</td>
                        <td>- <fmt:formatNumber value="${requestScope.discountAmount}" type="currency" currencySymbol="₫"/></td>
                    </tr>
                </c:if>
                <tr class="total-row">
                    <td colspan="3">Tổng cộng:</td>
                    <td><fmt:formatNumber value="${requestScope.totalAmount}" type="currency" currencySymbol="₫"/></td>
                </tr>
            </tbody>
        </table>

        <div class="coupon-section">
            <h3>Mã giảm giá</h3>
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                
                <input type="text" name="couponCode" placeholder="Nhập mã giảm giá" value="${requestScope.appliedCouponCode}">
                <button type="submit" name="action" value="applyCoupon">Áp dụng</button>
                <c:if test="${not empty requestScope.appliedCouponCode}">
                    <button type="submit" name="action" value="removeCoupon">Xóa mã</button>
                </c:if>
            </form>
            <c:if test="${not empty requestScope.couponMessage}">
                <p class="coupon-message ${requestScope.couponSuccess ? 'success' : 'error'}">${requestScope.couponMessage}</p>
            </c:if>
        </div>

        <form action="${pageContext.request.contextPath}/checkout/place-order" method="post">
            
            <c:if test="${not empty requestScope.appliedCouponCode}">
                <input type="hidden" name="appliedCouponCode" value="${requestScope.appliedCouponCode}">
                <input type="hidden" name="discountAmount" value="${requestScope.discountAmount}">
            </c:if>

            <h2 class="section-title">Địa chỉ giao hàng</h2>
            <div class="address-selection">
                <c:if test="${empty requestScope.addresses}">
                    <p>Bạn chưa có địa chỉ nào. Vui lòng <a href="${pageContext.request.contextPath}/profile/addresses/add">thêm địa chỉ mới</a>.</p>
                </c:if>
                <c:forEach var="address" items="${requestScope.addresses}">
                    <div class="address-item">
                        <input type="radio" id="shipping-${address.addressID}" name="shippingAddressId" value="${address.addressID}" 
                               <c:if test="${address.default}">checked</c:if>>
                        <label for="shipping-${address.addressID}">
                            <strong>${address.addressType}</strong>: ${address.addressLine1}, ${address.addressLine2} ${address.city}, ${address.state} ${address.postalCode}, ${address.country}
                            <c:if test="${address.default}"> (Mặc định)</c:if>
                        </label>
                    </div>
                </c:forEach>
                <div class="add-address-link"><a href="${pageContext.request.contextPath}/profile/addresses/add">Thêm địa chỉ mới</a></div>
            </div>

            <h2 class="section-title">Địa chỉ thanh toán</h2>
            <div class="address-selection">
                <c:if test="${empty requestScope.addresses}">
                    <p>Bạn chưa có địa chỉ nào. Vui lòng <a href="${pageContext.request.contextPath}/profile/addresses/add">thêm địa chỉ mới</a>.</p>
                </c:if>
                <c:forEach var="address" items="${requestScope.addresses}">
                    <div class="address-item">
                        <input type="radio" id="billing-${address.addressID}" name="billingAddressId" value="${address.addressID}" 
                               <c:if test="${address.default}">checked</c:if>>
                        <label for="billing-${address.addressID}">
                            <strong>${address.addressType}</strong>: ${address.addressLine1}, ${address.addressLine2} ${address.city}, ${address.state} ${address.postalCode}, ${address.country}
                            <c:if test="${address.default}"> (Mặc định)</c:if>
                        </label>
                    </div>
                </c:forEach>
                <div class="add-address-link"><a href="${pageContext.request.contextPath}/profile/addresses/add">Thêm địa chỉ mới</a></div>
            </div>

            <h2 class="section-title">Phương thức thanh toán</h2>
            <div class="payment-method">
                <input type="radio" id="cod" name="paymentMethod" value="COD" checked>
                <label for="cod">Thanh toán khi nhận hàng (COD)</label><br>
                <input type="radio" id="card" name="paymentMethod" value="Card">
                <label for="card">Thẻ tín dụng/ghi nợ</label><br>
            </div>

            <div class="payment-details-section" id="cardPaymentDetails" style="display: none;">
                <h3>Thông tin thẻ (Mô phỏng)</h3>
                <div class="form-group">
                    <label for="cardNumber">Số thẻ:</label>
                    <input type="text" id="cardNumber" name="cardNumber" placeholder="XXXX XXXX XXXX XXXX" pattern="[0-9]{13,19}" title="Số thẻ phải có từ 13 đến 19 chữ số">
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="expiryDate">Ngày hết hạn (MM/YY):</label>
                        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" pattern="(0[1-9]|1[0-2])\/[0-9]{2}" title="Ngày hết hạn phải ở định dạng MM/YY">
                    </div>
                    <div class="form-group">
                        <label for="cvv">CVV:</label>
                        <input type="text" id="cvv" name="cvv" placeholder="XXX" pattern="[0-9]{3,4}" title="CVV phải có 3 hoặc 4 chữ số">
                    </div>
                </div>
            </div>

            <h2 class="section-title">Ghi chú đơn hàng (Tùy chọn)</h2>
            <div class="notes-section">
                <label for="notes">Ghi chú:</label>
                <textarea id="notes" name="notes" rows="4" placeholder="Ghi chú về đơn hàng của bạn..."></textarea>
            </div>

            <button type="submit" class="place-order-btn">Đặt hàng</button>
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var codRadio = document.getElementById('cod');
            var cardRadio = document.getElementById('card');
            var cardPaymentDetails = document.getElementById('cardPaymentDetails');

            function toggleCardDetails() {
                if (cardRadio.checked) {
                    cardPaymentDetails.style.display = 'block';
                } else {
                    cardPaymentDetails.style.display = 'none';
                }
            }

            codRadio.addEventListener('change', toggleCardDetails);
            cardRadio.addEventListener('change', toggleCardDetails);

            // Initial check
            toggleCardDetails();
        });
    </script>
</body>
</html>