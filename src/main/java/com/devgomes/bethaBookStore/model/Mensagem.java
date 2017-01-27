package com.devgomes.bethaBookStore.model;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class Mensagem {
	
	private String mensagem;
	private Object objeto;
	private Response.Status status;
	
	
	public Mensagem(String mensagem, Status status) {
		this.mensagem = mensagem;
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Response.Status getStatus() {
		return status;
	}

	public void setStatus(Response.Status status) {
		this.status = status;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	
}
