<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Ký Thành Công</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }
        .container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 400px; }
        h2 { color: #28a745; margin-bottom: 20px; }
        p { color: #333; line-height: 1.6; }
        .login-link { margin-top: 20px; }
        .login-link a { color: #007bff; text-decoration: none; }
        .login-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Đăng Ký Thành Công!</h2>
        <c:if test="${not empty requestScope.successMessage}">
            <p>${requestScope.successMessage}</p>
        </c:if>
        <p>Cảm ơn bạn đã đăng ký tài khoản. Vui lòng kiểm tra email của bạn để xác minh tài khoản và hoàn tất quá trình đăng ký.</p>
        <div class="login-link">
            <a href="${pageContext.request.contextPath}/login">Quay lại trang Đăng nhập</a>
        </div>
    </div>
</body>
</html>
