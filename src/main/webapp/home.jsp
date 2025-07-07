<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang Chủ</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 960px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        .welcome-message { text-align: center; margin-bottom: 20px; }
        .user-info { border: 1px solid #ddd; padding: 15px; border-radius: 5px; margin-top: 20px; }
        .user-info p { margin: 5px 0; }
        .logout-button { display: block; width: 100px; margin: 20px auto; padding: 10px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; text-align: center; text-decoration: none; }
        .logout-button:hover { background-color: #c82333; }
        .profile-links { margin-top: 20px; text-align: center; }
        .profile-links a { display: inline-block; margin: 5px 10px; padding: 10px 15px; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px; }
        .profile-links a:hover { background-color: #0056b3; }
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
        <div class="welcome-message">
            <c:if test="${not empty sessionScope.user}">
                <h2>Xin chào, ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</h2>
                <p>Bạn đã đăng nhập thành công.</p>
                <div class="user-info">
                    <h3>Thông tin tài khoản:</h3>
                    <p><strong>Username:</strong> ${sessionScope.user.username}</p>
                    <p><strong>Email:</strong> ${sessionScope.user.email}</p>
                    <p><strong>Role:</strong> ${sessionScope.user.role}</p>
                    <p><strong>Trạng thái Email:</strong> <c:choose><c:when test="${sessionScope.user.emailVerified}">Đã xác minh</c:when><c:otherwise>Chưa xác minh</c:otherwise></c:choose></p>
                </div>
                <div class="profile-links">
                    <a href="${pageContext.request.contextPath}/profile">Hồ sơ của tôi</a>
                    <a href="${pageContext.request.contextPath}/profile/addresses">Quản lý Địa chỉ</a>
                    <c:if test="${sessionScope.user.role == 'Admin'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard">Bảng điều khiển Admin</a>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <h2>Chào mừng khách!</h2>
                <p>Vui lòng <a href="${pageContext.request.contextPath}/login">đăng nhập</a> hoặc <a href="${pageContext.request.contextPath}/register">đăng ký</a> để tiếp tục.</p>
            </c:if>
        </div>
        
        <c:if test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/logout" class="logout-button">Đăng Xuất</a>
        </c:if>
    </div>
</body>
</html>