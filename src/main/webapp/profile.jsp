<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hồ sơ của tôi</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 800px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .message { color: green; text-align: center; margin-bottom: 15px; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; }
        .profile-info, .profile-form { margin-top: 20px; padding: 20px; border: 1px solid #eee; border-radius: 8px; }
        .profile-info p { margin: 10px 0; }
        .profile-info .label { font-weight: bold; color: #555; }
        .edit-button { display: block; width: 150px; margin: 20px auto; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; text-align: center; text-decoration: none; }
        .edit-button:hover { background-color: #0056b3; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        .form-group input[type="text"], .form-group input[type="email"], .form-group input[type="tel"], .form-group input[type="date"], .form-group select {
            width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group button { padding: 10px 20px; background-color: #28a745; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #218838; }
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
        <h1>Hồ sơ của tôi</h1>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <div class="profile-info">
            <p><span class="label">Tên đăng nhập:</span> ${requestScope.userProfile.username}</p>
            <p><span class="label">Email:</span> ${requestScope.userProfile.email}</p>
            <p><span class="label">Họ và tên:</span> ${requestScope.userProfile.firstName} ${requestScope.userProfile.lastName}</p>
            <p><span class="label">Số điện thoại:</span> ${requestScope.userProfile.phoneNumber}</p>
            <p><span class="label">Ngày sinh:</span> <fmt:formatDate value="${requestScope.userProfileDateOfBirthUtil}" pattern="dd/MM/yyyy"/></p>
            <p><span class="label">Giới tính:</span> ${requestScope.userProfile.gender}</p>
            <p><span class="label">Vai trò:</span> ${requestScope.userProfile.role}</p>
            <p><span class="label">Email đã xác minh:</span> <c:choose><c:when test="${requestScope.userProfile.emailVerified}">Có</c:when><c:otherwise>Không</c:otherwise></c:choose></p>
            <p><span class="label">Trạng thái tài khoản:</span> <c:choose><c:when test="${requestScope.userProfile.active}">Hoạt động</c:when><c:otherwise>Không hoạt động</c:otherwise></c:choose></p>
        </div>

        <h2 style="text-align: center; margin-top: 30px;">Cập nhật Hồ sơ</h2>
        <div class="profile-form">
            <form action="${pageContext.request.contextPath}/profile/edit" method="post">
                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" value="${requestScope.userProfile.username}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${requestScope.userProfile.email}" required>
                </div>
                <div class="form-group">
                    <label for="firstName">Tên:</label>
                    <input type="text" id="firstName" name="firstName" value="${requestScope.userProfile.firstName}" required>
                </div>
                <div class="form-group">
                    <label for="lastName">Họ:</label>
                    <input type="text" id="lastName" name="lastName" value="${requestScope.userProfile.lastName}" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại:</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" value="${requestScope.userProfile.phoneNumber}">
                </div>
                <div class="form-group">
                    <label for="dateOfBirth">Ngày sinh:</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" value="<fmt:formatDate value='${requestScope.userProfileDateOfBirthUtil}' pattern='yyyy-MM-dd'/>">
                </div>
                <div class="form-group">
                    <label for="gender">Giới tính:</label>
                    <select id="gender" name="gender">
                        <option value="">Chọn giới tính</option>
                        <option value="Male" <c:if test="${requestScope.userProfile.gender == 'Male'}">selected</c:if>>Nam</option>
                        <option value="Female" <c:if test="${requestScope.userProfile.gender == 'Female'}">selected</c:if>>Nữ</option>
                        <option value="Other" <c:if test="${requestScope.userProfile.gender == 'Other'}">selected</c:if>>Khác</option>
                    </select>
                </div>
                <div class="form-group">
                    <button type="submit">Cập nhật Hồ sơ</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
