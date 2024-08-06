package org.example.petstore.service;

import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.dto.ReceiptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {

    @Autowired
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if (ctx != null) {
            orderService = ctx.getBean(OrderService.class);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int accountId = Integer.parseInt(req.getParameter("accountId"));
            int orderId = Integer.parseInt(req.getParameter("orderId"));

            ReceiptDto receipt = orderService.getReceipt(accountId, orderId);

            req.setAttribute("receipt", receipt);
            req.getRequestDispatcher("/WEB-INF/receipt.jsp").forward(req, resp);
        } catch (NoResultException e) {
            req.getRequestDispatcher("/WEB-INF/404.html").forward(req, resp);
        }

    }
}