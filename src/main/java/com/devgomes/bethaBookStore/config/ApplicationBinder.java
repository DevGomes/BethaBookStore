package com.devgomes.bethaBookStore.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.devgomes.bethaBookStore.dao.ClienteDAO;
import com.devgomes.bethaBookStore.dao.ItensPedidoDAO;
import com.devgomes.bethaBookStore.dao.LivroDAO;
import com.devgomes.bethaBookStore.dao.PedidoDAO;
import com.devgomes.bethaBookStore.services.LivroService;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(LivroDAO.class).to(LivroDAO.class);
		bind(LivroService.class).to(LivroService.class);
		bind(ClienteDAO.class).to(ClienteDAO.class);
		bind(ItensPedidoDAO.class).to(ItensPedidoDAO.class);
		bind(PedidoDAO.class).to(PedidoDAO.class);
	}
}
