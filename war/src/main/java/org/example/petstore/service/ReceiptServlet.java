package org.example.petstore.service;

import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.dto.ReceiptDto;
import org.example.petstore.mapper.OrderMapper;
import org.example.petstore.model.Order;

import java.io.IOException;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {

    private static final String PERSISTENCE_UNIT_NAME = "petstore";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        int accountId = Integer.parseInt(req.getParameter("accountId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        TypedQuery<Order> query = em.createQuery(
                "SELECT o FROM Order o " +
                        "JOIN FETCH o.customer a " +
                        "JOIN FETCH o.orderLineList ol " +
                        "JOIN FETCH ol.product " +
                        "WHERE o.customer.id = :accountId AND o.id = :orderId", Order.class
        );
        query.setParameter("accountId", accountId);
        query.setParameter("orderId", orderId);

        Order order;
        try {
            order = query.getSingleResult();
        } catch (NoResultException e) {
            em.close();
            req.getRequestDispatcher("/WEB-INF/404.html").forward(req, resp);
            return;
        }

        ReceiptDto receipt = OrderMapper.toDto(order);

        req.setAttribute("receipt", receipt);
        req.getRequestDispatcher("/WEB-INF/receipt.jsp").forward(req, resp);

        em.close();
    }
}
