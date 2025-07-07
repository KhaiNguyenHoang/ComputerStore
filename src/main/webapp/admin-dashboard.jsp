<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="admin-header.jsp" />

<div class="container-fluid">
    <h1 class="mb-4">Chào mừng đến với Bảng điều khiển Admin</h1>

    <div class="row dashboard-widgets g-4">
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Sản phẩm</h3>
                    <p class="card-text display-4 text-primary">120</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-primary mt-3">Xem Sản phẩm</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Đơn hàng</h3>
                    <p class="card-text display-4 text-info">50</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-info text-white mt-3">Xem Đơn hàng</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Người dùng</h3>
                    <p class="card-text display-4 text-success">30</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-success mt-3">Xem Người dùng</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Danh mục</h3>
                    <p class="card-text display-4 text-warning">10</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-warning text-white mt-3">Xem Danh mục</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Thương hiệu</h3>
                    <p class="card-text display-4 text-danger">5</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/brands" class="btn btn-danger mt-3">Xem Thương hiệu</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Đánh giá</h3>
                    <p class="card-text display-4 text-secondary">15</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/reviews" class="btn btn-secondary mt-3">Xem Đánh giá</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Mã giảm giá</h3>
                    <p class="card-text display-4 text-dark">7</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/coupons" class="btn btn-dark mt-3">Xem Mã giảm giá</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 col-lg-3">
            <div class="card widget shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">Tổng số Giao dịch Tồn kho</h3>
                    <p class="card-text display-4 text-primary">25</p> <%-- Placeholder --%>
                    <a href="${pageContext.request.contextPath}/admin/inventory" class="btn btn-primary mt-3">Xem Giao dịch Tồn kho</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="admin-footer.jsp" />