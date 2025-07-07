<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Danh Sách Sản Phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 15px 20px; text-align: center; }
        .header h1 { margin: 0; }
        .nav { background-color: #333; padding: 10px 20px; text-align: center; }
        .nav a { color: white; text-decoration: none; margin: 0 15px; font-weight: bold; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 1200px; margin: 20px auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        .search-filter-bar { display: flex; flex-wrap: wrap; justify-content: space-between; align-items: center; margin-bottom: 20px; padding: 15px; background-color: #e9ecef; border-radius: 8px; gap: 10px; }
        .search-filter-bar input[type="text"], .search-filter-bar select, .search-filter-bar button { padding: 8px; border: 1px solid #ced4da; border-radius: 4px; }
        .search-filter-bar button { background-color: #007bff; color: white; cursor: pointer; }
        .search-filter-bar button:hover { background-color: #0056b3; }
        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
        .product-card { border: 1px solid #ddd; border-radius: 8px; padding: 15px; text-align: center; background-color: #fff; box-shadow: 0 2px 5px rgba(0,0,0,0.05); }
        .product-card img { max-width: 100%; height: 180px; object-fit: contain; margin-bottom: 10px; }
        .product-card h3 { font-size: 1.2em; margin: 10px 0; }
        .product-card h3 a { text-decoration: none; color: #007bff; }
        .product-card h3 a:hover { text-decoration: underline; }
        .product-card .price { font-size: 1.1em; color: #dc3545; font-weight: bold; margin-bottom: 10px; }
        .product-card .category, .product-card .brand { font-size: 0.9em; color: #666; }
        .no-products { text-align: center; color: #555; font-size: 1.2em; margin-top: 50px; }
        .add-to-cart-btn { background-color: #28a745; color: white; padding: 8px 15px; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; margin-top: 10px; }
        .add-to-cart-btn:hover { background-color: #218838; }
        .pagination { display: flex; justify-content: center; align-items: center; margin-top: 30px; gap: 10px; }
        .pagination a, .pagination span { padding: 8px 12px; border: 1px solid #ddd; text-decoration: none; color: #007bff; border-radius: 4px; }
        .pagination a:hover { background-color: #e9ecef; }
        .pagination .current-page { background-color: #007bff; color: white; border-color: #007bff; }
        .pagination .disabled { color: #ccc; pointer-events: none; }
    </style>
</head>
<body>
    <div class="header">
        <h1>ComputerStore</h1>
    </div>
    <div class="nav">
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/products">Sản Phẩm</a>
        <a href="${pageContext.request.contextPath}/cart">Giỏ Hàng</a>
        <a href="${pageContext.request.contextPath}/orders">Đơn Hàng</a>
        <a href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
    </div>

    <div class="container">
        <h1>Danh Sách Sản Phẩm</h1>

        <div class="search-filter-bar">
            <form action="${pageContext.request.contextPath}/products" method="get" style="display: flex; flex-wrap: wrap; gap: 10px; width: 100%;">
                <input type="text" name="searchTerm" placeholder="Tìm kiếm sản phẩm..." value="${selectedSearchTerm}" style="flex-grow: 1;">
                
                <select name="categoryId">
                    <option value="">Tất cả Danh mục</option>
                    <c:forEach var="category" items="${requestScope.categories}">
                        <option value="${category.categoryID}" <c:if test="${category.categoryID == selectedCategoryId}">selected</c:if>>${category.categoryName}</option>
                    </c:forEach>
                </select>
                
                <select name="brandId">
                    <option value="">Tất cả Thương hiệu</option>
                    <c:forEach var="brand" items="${requestScope.brands}">
                        <option value="${brand.brandID}" <c:if test="${brand.brandID == selectedBrandId}">selected</c:if>>${brand.brandName}</option>
                    </c:forEach>
                </select>

                <select name="sortBy">
                    <option value="ProductName" <c:if test="${sortBy == 'ProductName'}">selected</c:if>>Sắp xếp theo Tên</option>
                    <option value="Price" <c:if test="${sortBy == 'Price'}">selected</c:if>>Sắp xếp theo Giá</option>
                    <option value="CreatedDate" <c:if test="${sortBy == 'CreatedDate'}">selected</c:if>>Sắp xếp theo Ngày tạo</option>
                </select>

                <select name="sortOrder">
                    <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Tăng dần</option>
                    <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Giảm dần</option>
                </select>
                
                <button type="submit">Tìm kiếm & Lọc</button>
            </form>
        </div>

        <c:if test="${empty requestScope.productList}">
            <p class="no-products">Không có sản phẩm nào để hiển thị.</p>
        </c:if>

        <div class="product-grid">
            <c:forEach var="product" items="${requestScope.productList}">
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/product-detail?id=${product.productID}">
                        <c:set var="mainImage" value="${null}"/>
                        <c:forEach var="img" items="${product.images}">
                            <c:if test="${img.mainImage}">
                                <c:set var="mainImage" value="${img}"/>
                            </c:if>
                        </c:forEach>
                        <c:choose>
                            <c:when test="${not empty mainImage}">
                                <img src="${pageContext.request.contextPath}/static/images/${mainImage.imageURL.substring(mainImage.imageURL.lastIndexOf('/') + 1)}" alt="${product.productName}">
                            </c:when>
                            <c:otherwise>
                                <img src="https://via.placeholder.com/200x200?text=No+Image" alt="${product.productName}">
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <h3><a href="${pageContext.request.contextPath}/product-detail?id=${product.productID}"><c:out value="${product.productName}"/></a></h3>
                    <p class="price">
                        <fmt:setLocale value="vi_VN"/>
                        <fmt:formatNumber value="${product.price}" type="currency"/>
                    </p>
                    <p class="category">Danh mục: 
                        <c:choose>
                            <c:when test="${not empty product.category}">
                                ${product.category.categoryName}
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p class="brand">Thương hiệu: 
                        <c:choose>
                            <c:when test="${not empty product.brand}">
                                ${product.brand.brandName}
                            </c:when>
                            <c:otherwise>
                                N/A
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <form action="${pageContext.request.contextPath}/cart/add" method="post">
                        <input type="hidden" name="productId" value="${product.productID}">
                        <input type="hidden" name="quantity" value="1"> <%-- Default to 1 item --%>
                        <button type="submit" class="add-to-cart-btn">Thêm vào giỏ hàng</button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="pagination">
            <c:url var="prevUrl" value="${pageContext.request.contextPath}/products">
                <c:param name="searchTerm" value="${selectedSearchTerm}"/>
                <c:param name="categoryId" value="${selectedCategoryId}"/>
                <c:param name="brandId" value="${selectedBrandId}"/>
                <c:param name="sortBy" value="${sortBy}"/>
                <c:param name="sortOrder" value="${sortOrder}"/>
                <c:param name="page" value="${currentPage - 1}"/>
                <c:param name="size" value="${pageSize}"/>
            </c:url>
            <c:url var="nextUrl" value="${pageContext.request.contextPath}/products">
                <c:param name="searchTerm" value="${selectedSearchTerm}"/>
                <c:param name="categoryId" value="${selectedCategoryId}"/>
                <c:param name="brandId" value="${selectedBrandId}"/>
                <c:param name="sortBy" value="${sortBy}"/>
                <c:param name="sortOrder" value="${sortOrder}"/>
                <c:param name="page" value="${currentPage + 1}"/>
                <c:param name="size" value="${pageSize}"/>
            </c:url>

            <c:if test="${currentPage > 1}">
                <a href="${prevUrl}">Trước</a>
            </c:if>
            <c:if test="${currentPage <= 1}">
                <span class="disabled">Trước</span>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:url var="pageUrl" value="${pageContext.request.contextPath}/products">
                    <c:param name="searchTerm" value="${selectedSearchTerm}"/>
                    <c:param name="categoryId" value="${selectedCategoryId}"/>
                    <c:param name="brandId" value="${selectedBrandId}"/>
                    <c:param name="sortBy" value="${sortBy}"/>
                    <c:param name="sortOrder" value="${sortOrder}"/>
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${pageSize}"/>
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