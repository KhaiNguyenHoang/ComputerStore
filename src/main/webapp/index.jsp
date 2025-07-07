<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chào mừng đến với ComputerStore</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }
        .container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 500px; }
        h1 { color: #007bff; margin-bottom: 20px; }
        p { color: #333; line-height: 1.6; }
        .links a { display: inline-block; margin: 10px; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }
        .links a:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Chào mừng đến với ComputerStore!</h1>
        <p>Đây là trang chủ của ứng dụng. Vui lòng chọn một tùy chọn bên dưới để tiếp tục.</p>
        <div class="links">
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
            <a href="${pageContext.request.contextPath}/products">Xem Sản phẩm</a>
        </div>
    </div>
</body>
</html>
