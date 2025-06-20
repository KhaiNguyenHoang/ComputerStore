<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }

        .container {
            max-width: 400px;
            margin: auto;
        }

        .error {
            color: red;
        }

        .message {
            color: green;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p class="message">${message}</p>
    </c:if>
    <form action="login" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>
        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>
    <p><a href="forgot-password">Forgot Password?</a></p>
    <p><a href="register">Register</a></p>
</div>
</body>
</html>