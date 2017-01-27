package com.devgomes.bethaBookStore.dao;

import javax.persistence.NoResultException;

import com.devgomes.bethaBookStore.model.Cliente;

public class ClienteDAO extends DefaultDAO {
	
	public ClienteDAO() {
		super();
	}
	
	public Cliente buscarCliente(String email) {
		
		try {
			return this.getSession()
					.createNamedQuery("Cliente.buscarPorEmail", Cliente.class)
					.setParameter("email", "%" + email + "%")
					.getSingleResult();
			
		}catch(NoResultException exception) {
			return null;
		}
	}
	
	public Cliente salvarCliente(Cliente cliente) {
		cliente = (Cliente) salvar(cliente);
		
		return cliente;
	}
}
