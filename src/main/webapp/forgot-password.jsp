<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
        }
        .navbar {
            background-color: #212529 !important;
        }
        .navbar-brand, .nav-link {
            color: #ffffff !important;
        }
        .navbar-brand:hover, .nav-link:hover {
            color: #cccccc !important;
        }
        .main-content {
            min-height: calc(100vh - 150px); /* Adjust based on header/footer height */
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 50px 0;
        }
        .forgot-password-card {
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            max-width: 450px;
            width: 100%;
        }
        .forgot-password-card h2 {
            text-align: center;
            color: #343a40;
            margin-bottom: 30px;
            font-weight: 700;
        }
        .forgot-password-card .form-label {
            font-weight: 500;
        }
        .forgot-password-card .btn-primary {
            width: 100%;
            padding: 10px;
            font-size: 1.1rem;
            margin-top: 15px;
        }
        .forgot-password-card .text-center a {
            color: #007bff;
            text-decoration: none;
        }
        .forgot-password-card .text-center a:hover {
            text-decoration: underline;
        }
        .footer {
            background-color: #212529;
            color: white;
            padding: 30px 0;
            text-align: center;
            margin-top: 50px;
        }
        .footer a {
            color: #cccccc;
            text-decoration: none;
        }
        .footer a:hover {
            color: #ffffff;
        }
    </style>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="main-content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-5">
                    <div class="forgot-password-card">
                        <h2>Quên Mật Khẩu</h2>
                        <c:if test="${not empty requestScope.errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${requestScope.errorMessage}
                            </div>
                        </c:if>
                        <c:if test="${not empty requestScope.successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${requestScope.successMessage}
                            </div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                            <div class="mb-3">
                                <label for="email" class="form-label">Nhập địa chỉ email của bạn:</label>
                                <input type="email" class="form-control" id="email" name="email" required value="${param.email}">
                            </div>
                            <button type="submit" class="btn btn-primary">Gửi Yêu Cầu Đặt Lại Mật Khẩu</button>
                        </form>
                        <div class="text-center mt-3">
                            <a href="${pageContext.request.contextPath}/login">Quay lại Đăng nhập</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>