<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:choose><c:when test="${not empty requestScope.address}">Sửa Địa chỉ</c:when><c:otherwise>Thêm Địa chỉ mới</c:otherwise></c:choose></title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 500px; }
        h1 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; }
        .form-group input[type="text"], .form-group select {
            width: calc(100% - 20px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group input[type="checkbox"] { margin-right: 5px; }
        .form-group button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .message { color: red; text-align: center; margin-bottom: 15px; }
        .success-message { color: green; text-align: center; margin-bottom: 15px; }
        .back-link { text-align: center; margin-top: 20px; }
        .back-link a { color: #007bff; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h1><c:choose><c:when test="${not empty requestScope.address}">Sửa Địa chỉ</c:when><c:otherwise>Thêm Địa chỉ mới</c:otherwise></c:choose></h1>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>
        <c:if test="${not empty requestScope.successMessage}">
            <p class="success-message">${requestScope.successMessage}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/profile/addresses/<c:choose><c:when test="${not empty requestScope.address}">edit</c:when><c:otherwise>add</c:otherwise></c:choose>" method="post">
            <c:if test="${not empty requestScope.address}">
                <input type="hidden" name="addressId" value="${requestScope.address.addressID}">
            </c:if>
            <div class="form-group">
                <label for="addressType">Loại địa chỉ:</label>
                <select id="addressType" name="addressType" required>
                    <option value="">Chọn loại địa chỉ</option>
                    <option value="Shipping" <c:if test="${requestScope.address.addressType == 'Shipping'}">selected</c:if>>Giao hàng</option>
                    <option value="Billing" <c:if test="${requestScope.address.addressType == 'Billing'}">selected</c:if>>Thanh toán</option>
                    <option value="Home" <c:if test="${requestScope.address.addressType == 'Home'}">selected</c:if>>Nhà riêng</option>
                    <option value="Work" <c:if test="${requestScope.address.addressType == 'Work'}">selected</c:if>>Cơ quan</option>
                </select>
            </div>
            <div class="form-group">
                <label for="addressLine1">Địa chỉ dòng 1:</label>
                <input type="text" id="addressLine1" name="addressLine1" value="${requestScope.address.addressLine1}" required>
            </div>
            <div class="form-group">
                <label for="addressLine2">Địa chỉ dòng 2 (Tùy chọn):</label>
                <input type="text" id="addressLine2" name="addressLine2" value="${requestScope.address.addressLine2}">
            </div>
            <div class="form-group">
                <label for="city">Thành phố:</label>
                <input type="text" id="city" name="city" value="${requestScope.address.city}" required>
            </div>
            <div class="form-group">
                <label for="state">Tỉnh/Bang (Tùy chọn):</label>
                <input type="text" id="state" name="state" value="${requestScope.address.state}">
            </div>
            <div class="form-group">
                <label for="postalCode">Mã bưu chính (Tùy chọn):</label>
                <input type="text" id="postalCode" name="postalCode" value="${requestScope.address.postalCode}">
            </div>
            <div class="form-group">
                <label for="country">Quốc gia:</label>
                <input type="text" id="country" name="country" value="${requestScope.address.country}" required>
            </div>
            <div class="form-group">
                <input type="checkbox" id="isDefault" name="isDefault" <c:if test="${requestScope.address.default}">checked</c:if>>
                <label for="isDefault">Đặt làm địa chỉ mặc định</label>
            </div>
            <div class="form-group">
                <button type="submit"><c:choose><c:when test="${not empty requestScope.address}">Cập nhật Địa chỉ</c:when><c:otherwise>Thêm Địa chỉ</c:otherwise></c:choose></button>
            </div>
        </form>
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/profile/addresses">Quay lại danh sách địa chỉ</a>
        </div>
    </div>
</body>
</html>
