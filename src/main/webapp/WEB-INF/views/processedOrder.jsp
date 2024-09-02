<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Order Processed</title>
</head>
<body>
<h1>Order Processed</h1>
<p><strong>Order ID:</strong> ${order.orderId}</p>
<p><strong>Total Amount:</strong> ${order.totalAmount}</p>
<p><strong>Discount Applied:</strong> ${discountApplied ? 'Yes' : 'No'}</p>
<p><strong>Validation Message:</strong> ${validationMessage}</p>
</body>
</html>
