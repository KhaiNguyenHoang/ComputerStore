<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lỗi</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; text-align: center; }
        .container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 400px; }
        h2 { color: #dc3545; margin-bottom: 20px; }
        p { color: #333; line-height: 1.6; }
        .back-link { margin-top: 20px; }
        .back-link a { color: #007bff; text-decoration: none; }
        .back-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Đã xảy ra lỗi!</h2>
        <c:if test="${not empty requestScope.error}">
            <p>${requestScope.error}</p>
        </c:if>
        <c:if test="${empty requestScope.error}">
            <p>Có vẻ như đã có một vấn đề xảy ra. Vui lòng thử lại sau.</p>
        </c:if>
        <div class="back-link">
            <a href="javascript:history.back()">Quay lại trang trước</a>
        </div>
    </div>
</body>
</html>
