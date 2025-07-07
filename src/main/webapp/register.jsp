<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Ký</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; padding: 20px 0; box-sizing: border-box; }
        .register-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 400px; max-width: 90%; margin: auto; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; }
        .form-group input[type="text"], .form-group input[type="email"], .form-group input[type="password"], .form-group input[type="tel"], .form-group input[type="date"] {
            width: calc(100% - 20px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group select {
            width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px;
        }
        .form-group button { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .form-group button:hover { background-color: #0056b3; }
        .message { color: red; text-align: center; margin-bottom: 15px; }
        .success-message { color: green; text-align: center; margin-bottom: 15px; }
        .login-link { text-align: center; margin-top: 20px; }
        .login-link a { color: #007bff; text-decoration: none; }
        .login-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>Đăng Ký Tài Khoản</h2>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="message">${requestScope.errorMessage}</p>
        </c:if>
        <c:if test="${not empty requestScope.successMessage}">
            <p class="success-message">${requestScope.successMessage}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/register" method="post">
            
            <div class="form-group">
                <label for="username">Tên đăng nhập:</label>
                <input type="text" id="username" name="username" required value="${param.username}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required value="${param.email}">
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <div class="form-group">
                <label for="firstName">Tên:</label>
                <input type="text" id="firstName" name="firstName" value="${param.firstName}">
            </div>
            <div class="form-group">
                <label for="lastName">Họ:</label>
                <input type="text" id="lastName" name="lastName" value="${param.lastName}">
            </div>
            <div class="form-group">
                <label for="phoneNumber">Số điện thoại:</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="${param.phoneNumber}">
            </div>
            <div class="form-group">
                <label for="dateOfBirth">Ngày sinh:</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth" value="${param.dateOfBirth}">
            </div>
            <div class="form-group">
                <label for="gender">Giới tính:</label>
                <select id="gender" name="gender">
                    <option value="">Chọn giới tính</option>
                    <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Nam</option>
                    <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Nữ</option>
                    <option value="Other" ${param.gender == 'Other' ? 'selected' : ''}>Khác</option>
                </select>
            </div>
            <div class="form-group">
                <button type="submit">Đăng Ký</button>
            </div>
        </form>
        <div class="login-link">
            Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
        </div>
    </div>
</body>
</html>
