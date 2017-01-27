package com.devgomes.bethaBookStore.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class InicializarAplicacao extends ResourceConfig {
	
	public InicializarAplicacao() {
		packages("com.devgomes.bethaBookStore.controllers").
		register(new ApplicationBinder());
	}	
}
