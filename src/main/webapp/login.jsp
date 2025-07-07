<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Nhập</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 350px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; }
        .form-group input[type="text"], .form-group input[type="password"] {
            width: calc(100% - 20px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .message { color: red; text-align: center; margin-bottom: 15px; }
        .success-message { color: green; text-align: center; margin-bottom: 15px; }
        .register-link, .forgot-password-link { text-align: center; margin-top: 10px; }
        .register-link a, .forgot-password-link a { color: #007bff; text-decoration: none; }
        .register-link a:hover, .forgot-password-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Đăng Nhập</h2>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>
        <c:if test="${not empty requestScope.successMessage}">
            <p class="success-message">${requestScope.successMessage}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            
            <div class="form-group">
                <label for="username">Tên đăng nhập:</label>
                <input type="text" id="username" name="username" required value="${param.username}">
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <button type="submit">Đăng Nhập</button>
            </div>
        </form>
        <div class="forgot-password-link">
            <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
        </div>
        <div class="register-link">
            Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
        </div>
    </div>
</body>
</html>