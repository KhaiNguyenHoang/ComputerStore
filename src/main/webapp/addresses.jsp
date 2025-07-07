<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Địa chỉ của bạn</title>
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
        .address-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; }
        .address-card { border: 1px solid #ddd; border-radius: 8px; padding: 15px; background-color: #f9f9f9; position: relative; }
        .address-card h3 { margin-top: 0; color: #007bff; }
        .address-card p { margin: 5px 0; }
        .address-card .default-badge { background-color: #28a745; color: white; padding: 5px 10px; border-radius: 5px; font-size: 0.8em; position: absolute; top: 10px; right: 10px; }
        .address-actions { margin-top: 15px; }
        .address-actions a, .address-actions button { display: inline-block; padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; text-decoration: none; margin-right: 5px; }
        .address-actions .edit-btn { background-color: #ffc107; color: #333; }
        .address-actions .edit-btn:hover { background-color: #e0a800; }
        .address-actions .delete-btn { background-color: #dc3545; color: white; }
        .address-actions .delete-btn:hover { background-color: #c82333; }
        .address-actions .set-default-btn { background-color: #17a2b8; color: white; }
        .address-actions .set-default-btn:hover { background-color: #138496; }
        .add-address-link { display: block; text-align: center; margin-top: 30px; }
        .add-address-link a { background-color: #28a745; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none; font-weight: bold; }
        .add-address-link a:hover { background-color: #218838; }
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
        <a href="#">Đơn Hàng</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
    </div>

    <div class="container">
        <h1>Quản lý Địa chỉ của bạn</h1>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <c:if test="${empty requestScope.addresses}">
            <p style="text-align: center;">Bạn chưa có địa chỉ nào được lưu.</p>
        </c:if>

        <div class="address-list">
            <c:forEach var="address" items="${requestScope.addresses}">
                <div class="address-card">
                    <c:if test="${address.default}">
                        <span class="default-badge">Mặc định</span>
                    </c:if>
                    <h3>${address.addressType}</h3>
                    <p>${address.addressLine1}</p>
                    <c:if test="${not empty address.addressLine2}">
                        <p>${address.addressLine2}</p>
                    </c:if>
                    <p>${address.city}, ${address.state} ${address.postalCode}</p>
                    <p>${address.country}</p>
                    <div class="address-actions">
                        <a href="${pageContext.request.contextPath}/profile/addresses/edit?id=${address.addressID}" class="edit-btn">Sửa</a>
                        <a href="${pageContext.request.contextPath}/profile/addresses/delete?id=${address.addressID}" class="delete-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa địa chỉ này không?');">Xóa</a>
                        <c:if test="${!address.default}">
                            <a href="${pageContext.request.contextPath}/profile/addresses/set-default?id=${address.addressID}" class="set-default-btn">Đặt làm mặc định</a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="add-address-link">
            <a href="${pageContext.request.contextPath}/profile/addresses/add">Thêm địa chỉ mới</a>
        </div>
    </div>
</body>
</html>
