<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên Mật Khẩu</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 400px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; }
        .form-group input[type="email"] {
            width: calc(100% - 20px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .message { color: red; text-align: center; margin-bottom: 15px; }
        .success-message { color: green; text-align: center; margin-bottom: 15px; }
        .back-to-login { text-align: center; margin-top: 20px; }
        .back-to-login a { color: #007bff; text-decoration: none; }
        .back-to-login a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Quên Mật Khẩu</h2>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>
        <c:if test="${not empty requestScope.successMessage}">
            <p class="success-message">${requestScope.successMessage}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
            
            <div class="form-group">
                <label for="email">Nhập địa chỉ email của bạn:</label>
                <input type="email" id="email" name="email" required value="${param.email}">
            </div>
            <div class="form-group">
                <button type="submit">Gửi Yêu Cầu Đặt Lại Mật Khẩu</button>
            </div>
        </form>
        <div class="back-to-login">
            <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a>
        </div>
    </div>
</body>
</html>