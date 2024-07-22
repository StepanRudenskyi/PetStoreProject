package org.example.petstore;

import org.example.petstore.model.Account;
import org.example.petstore.service.AccountService;
import org.example.petstore.service.AccountServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.lang.reflect.Proxy;

public class JpaApp {

	
	public static void main(String[] args) {
	  new JpaApp().demo1();	
	}

	private void demo1() {

		AccountService accountServiceImpl = new AccountServiceImpl();

		AccountService accountServiceProxy = (AccountService) Proxy.newProxyInstance(
				AccountService.class.getClassLoader(),
				new Class[]{AccountService.class},
                new TimingDynamicInvocationHandler(accountServiceImpl));


		Account acc = new Account();
		acc.setFirstName("John");
		acc.setLastName("Doe");
		acc.setEmail("user@usa.net");

		accountServiceProxy.saveAccount(acc);
		
		
		
	}

}
