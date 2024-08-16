<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Receipt</title>
</head>
<body>
<jsp:useBean id="receiptBean" class="org.example.petstore.service.ReceiptBean" scope="request"/>

<h1>Receipt for ${receiptBean.receipt.accountFirstName} ${receiptBean.receipt.accountLastName}</h1>
<p>Order ID: ${receiptBean.receipt.orderId}</p>
<p>Order Date: ${receiptBean.receipt.orderDate} </p>
<p>Status: ${receiptBean.receipt.status}</p>
<p>Total Amount: ${receiptBean.receipt.totalAmount}</p>

<h2>Order Line:</h2>
<ul>
    <c:forEach var="line" items="${ receiptBean.receipt.orderLines }">
        <li>${line.productName}: ${line.quantity} x $${line.price}</li>
    </c:forEach>
</ul>

</body>
</html>
