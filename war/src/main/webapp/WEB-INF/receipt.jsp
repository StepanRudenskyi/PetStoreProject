<%@ page import="org.example.petstore.dto.ReceiptDto" %>
<%--
  Created by IntelliJ IDEA.
  User: srude
  Date: 8/2/2024
  Time: 4:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% ReceiptDto receipt = (ReceiptDto) request.getAttribute("receipt"); %>

<html>
<head>
    <title>Receipt</title>
</head>
<body>
<h1>Receipt for <%= receipt.getAccountFirstName() %> <%= receipt.getAccountLastName() %>
</h1>
<p>Order ID: <%= receipt.getOrderId()%>
</p>
<P>Order Date: <%= receipt.getOrderDate()%>
</P>
<P>Status: <%= receipt.getStatus()%>
</P>
<P>Total Amount: <%= receipt.getTotalAmount() %>
</P>

<h2>Order Line:</h2>
<ul>
    <%
        for (ReceiptDto.OrderLineDto line : receipt.getOrderLines()) {
    %>
        <li><%= line.getProductName() %>: <%= line.getQuantity() %> x $<%=line.getPrice()%></li>
    <%
        }
    %>
</ul>
</body>
</html>
