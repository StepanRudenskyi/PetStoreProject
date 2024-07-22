package org.example.petstore.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.petstore.model.Account;

public class AccountServiceImpl implements AccountService {
    private EntityManager em;
    public AccountServiceImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("petstore");
        this.em = emf.createEntityManager();
    }


    @Override
    public void saveAccount(Account account) {
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }
}
