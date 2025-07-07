<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Thương hiệu - Admin</title>
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
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .message { color: green; text-align: center; margin-bottom: 15px; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; }
        .add-button { display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px; margin-bottom: 20px; }
        .add-button:hover { background-color: #218838; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-buttons a, .action-buttons button { padding: 5px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; text-decoration: none; margin-right: 5px; }
        .action-buttons .edit-btn { background-color: #ffc107; color: #333; }
        .action-buttons .edit-btn:hover { background-color: #e0a800; }
        .action-buttons .delete-btn { background-color: #dc3545; color: white; }
        .action-buttons .delete-btn:hover { background-color: #c82333; }
        .status-active { color: green; font-weight: bold; }
        .status-inactive { color: red; font-weight: bold; }
        .pagination { display: flex; justify-content: center; align-items: center; margin-top: 30px; gap: 10px; }
        .pagination a, .pagination span { padding: 8px 12px; border: 1px solid #ddd; text-decoration: none; color: #007bff; border-radius: 4px; }
        .pagination a:hover { background-color: #e9ecef; }
        .pagination .current-page { background-color: #007bff; color: white; border-color: #007bff; }
        .pagination .disabled { color: #ccc; pointer-events: none; }
        .sort-controls { margin-bottom: 15px; text-align: right; }
        .sort-controls select { padding: 5px; border-radius: 4px; border: 1px solid #ddd; }
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
            <h1>Quản lý Thương hiệu</h1>
        </div>

        <c:if test="${not empty param.message}">
            <p class="message">${param.message}</p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="error-message">${requestScope.errorMessage}</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin/brands/add" class="add-button">Thêm Thương hiệu mới</a>

        <div class="sort-controls">
            <form action="${pageContext.request.contextPath}/admin/brands" method="get" style="display: inline;">
                <label for="sortBy">Sắp xếp theo:</label>
                <select name="sortBy" id="sortBy" onchange="this.form.submit()">
                    <option value="BrandName" <c:if test="${sortBy == 'BrandName'}">selected</c:if>>Tên Thương hiệu</option>
                    <option value="CreatedDate" <c:if test="${sortBy == 'CreatedDate'}">selected</c:if>>Ngày tạo</option>
                </select>
                <select name="sortOrder" onchange="this.form.submit()">
                    <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Tăng dần</option>
                    <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Giảm dần</option>
                </select>
                <input type="hidden" name="page" value="${currentPage}">
                <input type="hidden" name="size" value="${pageSize}">
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên Thương hiệu</th>
                    <th>Mô tả</th>
                    <th>Website</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="brand" items="${requestScope.brandList}">
                    <tr>
                        <td>${brand.brandID}</td>
                        <td>${brand.brandName}</td>
                        <td>${brand.description}</td>
                        <td>${brand.website}</td>
                        <td>
                            <c:choose>
                                <c:when test="${brand.active}"><span class="status-active">Hoạt động</span></c:when>
                                <c:otherwise><span class="status-inactive">Không hoạt động</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/brands/edit?id=${brand.brandID}" class="edit-btn">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/brands/delete?id=${brand.brandID}" class="delete-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa thương hiệu này không?');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty requestScope.brandList}">
                    <tr>
                        <td colspan="6">Không có thương hiệu nào để hiển thị.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="pagination">
            <c:url var="prevUrl" value="${pageContext.request.contextPath}/admin/brands">
                <c:param name="page" value="${currentPage - 1}"/>
                <c:param name="size" value="${pageSize}"/>
                <c:param name="sortBy" value="${sortBy}"/>
                <c:param name="sortOrder" value="${sortOrder}"/>
            </c:url>
            <c:url var="nextUrl" value="${pageContext.request.contextPath}/admin/brands">
                <c:param name="page" value="${currentPage + 1}"/>
                <c:param name="size" value="${pageSize}"/>
                <c:param name="sortBy" value="${sortBy}"/>
                <c:param name="sortOrder" value="${sortOrder}"/>
            </c:url>

            <c:if test="${currentPage > 1}">
                <a href="${prevUrl}">Trước</a>
            </c:if>
            <c:if test="${currentPage <= 1}">
                <span class="disabled">Trước</span>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:url var="pageUrl" value="${pageContext.request.contextPath}/admin/brands">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${pageSize}"/>
                    <c:param name="sortBy" value="${sortBy}"/>
                    <c:param name="sortOrder" value="${sortOrder}"/>
                </c:url>
                <a href="${pageUrl}" class="${i == currentPage ? 'current-page' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="${nextUrl}">Sau</a>
            </c:if>
            <c:if test="${currentPage >= totalPages}">
                <span class="disabled">Sau</span>
            </c:if>
        </div>
    </div>
</body>
</html>