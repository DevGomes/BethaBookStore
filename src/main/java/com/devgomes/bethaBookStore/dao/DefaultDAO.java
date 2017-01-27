package com.devgomes.bethaBookStore.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.devgomes.bethaBookStore.config.HibernateUtil;


public class DefaultDAO {
	
	private Session session;
	private Transaction transaction;
	
	protected DefaultDAO() {
		session = HibernateUtil.getSessionfactory().openSession();
	}
	
	protected Object salvar(Object obj) {
		iniciarTransacao();
		obj = session.merge(obj);
		transaction.commit();
		
		return obj;
	}
	
	protected void deletar(Object obj) {
		iniciarTransacao();
		session.delete(obj);
		transaction.commit();
	}
	
	private void iniciarTransacao() {
		transaction = session.beginTransaction();
	}

	public Session getSession() {
		return session;
	}

}
