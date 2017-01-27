package com.devgomes.bethaBookStore.dao;


import java.util.List;

import com.devgomes.bethaBookStore.model.Pedido;

public class PedidoDAO extends DefaultDAO {

	public PedidoDAO() {
		super();
	}
	
	public Pedido salvarPedido(Pedido pedidoCliente) {
		pedidoCliente = (Pedido) salvar(pedidoCliente);
		
		return pedidoCliente;
	}
	
	public List<Pedido> getListPedidos(Integer idCliente) {
		return getSession()
				.createNamedQuery("Pedido.BuscarPedidos")
				.setParameter("idCliente", idCliente)
				.getResultList();
	}
}
