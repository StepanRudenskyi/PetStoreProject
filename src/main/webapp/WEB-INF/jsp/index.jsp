<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Landing Page</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            color: #ff6347;
            margin-top: 20px;
            font-size: 2.5em;
        }

        h2.category-title {
            color: #676767;
            border-bottom: 2px solid #ddd;
            padding-bottom: 10px;
            margin-top: 20px;
            margin-left: 20px;
        }

        ul.product-list {
            list-style-type: none;
            padding: 0;
        }

        .product-item {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin: 10px 0;
            padding: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .product-name {
            font-weight: bold;
            color: #333;
        }

        .product-description {
            color: #666;
            margin: 10px 0;
        }

        .add-to-cart {
            display: inline-block;
            background-color: #ff6347;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }

        .add-to-cart:hover {
            background-color: #e5533b;
        }

    </style>

</head>
<body>
<h1>Products by Category</h1>
<c:forEach var="category" items="${categories}">
    <h2 class="category-title">${category.categoryName}</h2>
    <ul class="product-list">
        <c:forEach var="product" items="${category.products}">
            <li class="product-item">
                <p class="product-name">${product.name} - $${product.retailPrice}</p>
                <p class="product-description">${product.description}</p>
                <a class="add-to-cart" href="/addToCart?productId=${product.id}">Add to Cart</a>
            </li>
        </c:forEach>
    </ul>
</c:forEach>
</body>
</html>
