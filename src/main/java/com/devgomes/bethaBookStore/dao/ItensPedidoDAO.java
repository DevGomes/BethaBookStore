package com.devgomes.bethaBookStore.dao;

import java.util.List;

import com.devgomes.bethaBookStore.model.ItensPedido;

public class ItensPedidoDAO extends DefaultDAO {
	
	public ItensPedidoDAO() {
		super();
	}
	
	public ItensPedido salvarItensPedido(ItensPedido itensPedido) {
		ItensPedido itemPedido = (ItensPedido) salvar(itensPedido);
		
		return itemPedido;
	}
	
	public List<ItensPedido> getListItensPedido(Integer idPedido) {
		return getSession()
				.createNamedQuery("ItensPedido.buscarItensPedido")
				.setParameter("idPedido", idPedido)
				.getResultList();
	}
}


