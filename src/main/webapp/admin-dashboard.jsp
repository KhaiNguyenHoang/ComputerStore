<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f4f4; display: flex; }
        .sidebar { width: 200px; background-color: #333; color: white; padding: 20px; height: 100vh; position: fixed; }
        .sidebar h2 { text-align: center; margin-bottom: 30px; }
        .sidebar ul { list-style: none; padding: 0; }
        .sidebar ul li { margin-bottom: 15px; }
        .sidebar ul li a { color: white; text-decoration: none; display: block; padding: 10px; border-radius: 5px; }
        .sidebar ul li a:hover { background-color: #555; }
        .main-content { margin-left: 220px; padding: 20px; width: calc(100% - 220px); }
        .header-main { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; margin-bottom: 20px; border-radius: 8px; }
        .dashboard-widgets { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; }
        .widget { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); text-align: center; }
        .widget h3 { color: #333; margin-top: 0; }
        .widget p { font-size: 1.5em; font-weight: bold; color: #007bff; }
        .widget a { display: inline-block; margin-top: 15px; padding: 8px 15px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; }
        .widget a:hover { background-color: #5a6268; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Admin Panel</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/products">Quản lý Sản phẩm</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/categories">Quản lý Danh mục</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/brands">Quản lý Thương hiệu</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/users">Quản lý Người dùng</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/reviews">Quản lý Đánh giá</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/coupons">Quản lý Mã giảm giá</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/inventory">Quản lý Tồn kho</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a></li>
        </ul>
    </div>

    <div class="main-content">
        <div class="header-main">
            <h1>Chào mừng đến với Bảng điều khiển Admin</h1>
        </div>

        <div class="dashboard-widgets">
            <div class="widget">
                <h3>Tổng số Sản phẩm</h3>
                <p>120</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/products">Xem Sản phẩm</a>
            </div>
            <div class="widget">
                <h3>Tổng số Đơn hàng</h3>
                <p>50</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/orders">Xem Đơn hàng</a>
            </div>
            <div class="widget">
                <h3>Tổng số Người dùng</h3>
                <p>30</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/users">Xem Người dùng</a>
            </div>
            <div class="widget">
                <h3>Tổng số Danh mục</h3>
                <p>10</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/categories">Xem Danh mục</a>
            </div>
            <div class="widget">
                <h3>Tổng số Thương hiệu</h3>
                <p>5</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/brands">Xem Thương hiệu</a>
            </div>
            <div class="widget">
                <h3>Tổng số Đánh giá</h3>
                <p>15</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/reviews">Xem Đánh giá</a>
            </div>
            <div class="widget">
                <h3>Tổng số Mã giảm giá</h3>
                <p>7</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/coupons">Xem Mã giảm giá</a>
            </div>
            <div class="widget">
                <h3>Tổng số Giao dịch Tồn kho</h3>
                <p>25</p> <%-- Placeholder --%>
                <a href="${pageContext.request.contextPath}/admin/inventory">Xem Giao dịch Tồn kho</a>
            </div>
        </div>
    </div>
</body>
</html>