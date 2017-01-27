package com.devgomes.bethaBookStore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.devgomes.bethaBookStore.model.Cliente;
import com.devgomes.bethaBookStore.model.ItensPedido;
import com.devgomes.bethaBookStore.model.Livro;
import com.devgomes.bethaBookStore.model.Mensagem;
import com.devgomes.bethaBookStore.model.Pedido;
import com.devgomes.bethaBookStore.services.LivroService;

@Path("livros")
public class LivroController extends Application {
	
	@Inject
	private LivroService livroService; 
	
	@Context 
	private HttpServletRequest request;
	
	
	@PostConstruct
	private void inicializar() {
		livroService.inicializarRepositorioLivros();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public List<Livro> getListLivros() {
		
		livroService.criarSessaoCarrinho(request);
		
		return livroService.buscarTodosLivros();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("carrinhoSessao/")
	public List<Livro> getCarrinhoSessao(){
		
		return livroService.getCarrinhoSessao(request, "sessaoCarrinho");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("addCarrinho/")
	public List<Livro> addCarrinhoSessao(List<Livro> carrinhoCliente) {
		
		HttpSession session = request.getSession();
		session.setAttribute("sessaoCarrinho", carrinhoCliente);
		
		return carrinhoCliente;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("finalizarPedido/")
	public Mensagem finalizarPedido(Cliente cliente) {
		
		try {
			livroService.finalizarPedidoCliente(request, cliente);
		} catch (Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			return new Mensagem("Houve um erro ao finalizado seu pedido, consulte o administrador",
					Status.INTERNAL_SERVER_ERROR);
		}
		
		return new Mensagem(cliente.getNome() + ", seu pedido foi finalizado com sucesso, verifique no link pedidos", Status.OK);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pedidosClienteSessao/")
	public List<Pedido> getListaPedidosClienteSessao(){
		
		ArrayList<Pedido> pedidosCliente = null;
		
		try{
			pedidosCliente = livroService.buscarPedidosClienteSessao(request);
		} catch(Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		return pedidosCliente;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pedidosCliente/{email}/")
	public List<Pedido> getListaPedidosCliente(@PathParam("email") String email){
		
		ArrayList<Pedido> pedidosCliente = null;
		
		try{
			pedidosCliente = livroService.buscarPedidosCliente(email);
		} catch(Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		return pedidosCliente;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("detalhePedido/")
	public List<ItensPedido> getDetalhePedido(Pedido pedido) {
		
		ArrayList<ItensPedido> itensPedido = null;
		
		try{
			itensPedido = (ArrayList<ItensPedido>) livroService.buscarItensPedido(pedido);
		} catch(Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		return itensPedido;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("salvar/")
	public Response salvarLivro(Livro livro) {
		
		try {
			Long id = livroService.grvarLivro(livro);
			
			System.out.println("ID Novo Livro: " + id);
			
			return Response.status(Response.Status.OK).build();
		} catch (Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DELETE
	@Path("deletar/{id}/")
	public Response deletarLivro(@PathParam("id") Long id) {
		try {
			livroService.excluirLivro(id);
			return Response.status(Response.Status.OK).build();
		} catch (Exception exception) {
			Logger.getLogger(LivroController.class.getName()).log(Level.SEVERE, null, exception);
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
}
