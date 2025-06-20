<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Forgot Password</title>
  <style>
    body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }
    .error { color: red; }
    .message { color: green; }
  </style>
</head>
<body>
<h2>Reset Your Password</h2>
<c:if test="${not empty error}">
  <p class="error">${error}</p>
</c:if>
<c:if test="${not empty message}">
  <p class="message">${message}</p>
</c:if>
<form action="forgot-password" method="post">
  <label>Email: <input type="email" name="email" required></label><br>
  <input type="submit" value="Send Reset Link">
</form>
<a href="login">Back to Login</a>
</body>
</html>