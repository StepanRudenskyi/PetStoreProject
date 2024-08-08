<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receipt</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        h1 {
            color: #333;
        }

        p {
            margin: 0.5em 0;
        }

        ul {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        li {
            margin-bottom: 0.5em;
        }
    </style>
</head>
<body>
<h1>Receipt for ${receipt.accountFirstName} ${receipt.accountLastName}</h1>

<p>Order ID: ${receipt.orderId}</p>
<p>Order Date: ${receipt.orderDate}</p>
<p>Status: ${receipt.status}</p>
<p>Total Amount: $${receipt.totalAmount}</p>

<h2>Order Lines:</h2>
<ul>
    <c:forEach items="${receipt.orderLines}" var="line">
        <li>${line.productName}: ${line.quantity} x $${line.price}</li>
    </c:forEach>
</ul>
</body>
</html>
