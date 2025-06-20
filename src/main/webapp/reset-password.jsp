<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Reset Password</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }
        .error { color: red; }
        .message { color: green; }
    </style>
</head>
<body>
<h2>Set New Password</h2>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>
<form action="reset-password" method="post">
    <%String token = request.getParameter("token");%>
    <input type="hidden" name="token" value="<%=token%>">
    <label>New Password: <input type="password" name="newPassword" required></label><br>
    <label>Confirm Password: <input type="password" name="confirmPassword" required></label><br>
    <input type="submit" value="Reset Password">
</form>
<a href="forgot-password">Request a new reset link</a> | <a href="login">Back to Login</a>
</body>
</html>