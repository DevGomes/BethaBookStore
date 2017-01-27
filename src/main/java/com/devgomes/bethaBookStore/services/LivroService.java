package com.devgomes.bethaBookStore.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devgomes.bethaBookStore.dao.ClienteDAO;
import com.devgomes.bethaBookStore.dao.ItensPedidoDAO;
import com.devgomes.bethaBookStore.dao.LivroDAO;
import com.devgomes.bethaBookStore.dao.PedidoDAO;
import com.devgomes.bethaBookStore.model.Cliente;
import com.devgomes.bethaBookStore.model.ItensPedido;
import com.devgomes.bethaBookStore.model.Livro;
import com.devgomes.bethaBookStore.model.Pedido;

public class LivroService {
	
	@Inject private LivroDAO livroDAO;
	@Inject private ClienteDAO clienteDAO;
	@Inject private ItensPedidoDAO itensPedidoDAO;
	@Inject private PedidoDAO pedidoDAO;
	
	private List<Livro> cargaInicialLivros;
		
	
	public Long grvarLivro(Livro livro) {
		
		if(livro.getId() == null) {
			livro.setUrlCapaLivro("resources/img/livro-sem-capa.png");
		}
		
		return livroDAO.salvarLivro(livro);
	}
	
	private void gravarLivrosIniciais() {
		
		if(cargaInicialLivros == null) {
			return;
		}
		
		for (Livro livro : cargaInicialLivros) {
			livroDAO.salvarLivro(livro);
		}
	}
	
	public void inicializarRepositorioLivros() {
		
		List<Livro> listaLivros = livroDAO.getListaLivros();
		
		if(listaLivros == null || listaLivros.size() == 0) {
			cargaInicialLivros = new ArrayList<Livro>();
			
			cargaInicialLivros.add(new Livro(null, "HTML5 em Ação", "O livro HTML5 em Ação fornece "
									+ "uma introdução completa ao desenvolvimento web com o uso de HTML5.", 
									592, "Rob Crowther, Joe Lennon, Ash Blue, Greg Wanish", "Novatec", 
									new BigDecimal(119.00), 2014, "resources/img/livro-html5-acao.jpg"));
			cargaInicialLivros.add(new Livro(null, "CSS3", "Maujor aborda as novas funcionalidades das "
									+ "CSS3 de forma clara, em linguagem didática, mostrando "
									+ "vários exemplos práticos no site do livro.", 
									496, "Maurício Samy Silva", "Novatec", new BigDecimal(89.00), 2011,
									"resources/img/livro-css3.jpg"));
			cargaInicialLivros.add(new Livro(null, "Desenvolvendo jogos mobile com HTML5", "Este livro tem "
									+ "por objetivo apresentar ao leitor conceitos e práticas para desenvolvimento "
									+ "de jogos para dispositivos móveis.", 223, " Luiz Fernando Estevarengo", 
									"Novatec", new BigDecimal(57.00), 2016, "resources/img/livro-jogos-html5.jpg"));
			cargaInicialLivros.add(new Livro(null, "Java - Como Programar 8 Ed.", "A oitava edição de Java "
									+ "como programar, lançada pela Pearson Education, chega ao mercado com novo "
									+ "design e um traço inovador: preocupados em aguçar o raciocínio e o pensamento "
									+ "crítico dos estudantes.", 1176, "Paul Deitel", "Pearson Brasil", 
									new BigDecimal(100.00), 2010, "resources/img/java-como-programar.jpg"));
			cargaInicialLivros.add(new Livro(null, "Use a Cabeça Java", "'Use a Cabeça Java' é uma experiência "
									+ "completa de aprendizado em programação orientada a objetos (OO) e Java. ", 
									470, "Kathy Sierra", "Alta Books", new BigDecimal(85.30), 2005,
									"resources/img/livro-use-a-cabeca-java.jpg"));
			cargaInicialLivros.add(new Livro(null, "Use a Cabeça Padrões de Projetos (design Patterns) - 2ª Ed. Revisada ", 
									"Como você sabe que não quer reinventar a roda (ou, pior, um pneu furado), "
									+ "então você busca padrões de projetos.", 95, "Freeman,Elisabeth; Freeman, Eric",
									"Alta Books", new BigDecimal(75.00), 2007, 
									"resources/img/use-cabeca-padroes.jpg"));
			cargaInicialLivros.add(new Livro(null, "Código Limpo Habilidades Práticas do Agile Software "
									+ "- Edição Revisada ", "Mesmo um código ruim pode funcionar. Mas se ele "
									+ "não for limpo, pode acabar com uma empresa de desenvolvimento.", 
									440, "Martin,Robert Cecil", "Alta Books", new BigDecimal(75.00), 2009,
									"resources/img/livro-codigo-limpo.png"));
			
			gravarLivrosIniciais();
		}
	}
	
	public List<Livro> buscarTodosLivros() {
		return livroDAO.getListaLivros();
	}
	
	public List<Livro> getCargaInicialLivros() {
		return cargaInicialLivros;
	}
	
	public void excluirLivro(Long id) {
		Livro livroSelecionado = livroDAO.getLivroPorId(id);
		livroDAO.deletarLivro(livroSelecionado);
	}
	
	public void criarSessaoCarrinho(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		ArrayList<Livro> carrinho = (ArrayList<Livro>) session.getAttribute("sessaoCarrinho"); 
		
		if(carrinho == null) {
			session.setAttribute("sessaoCarrinho", new ArrayList<Livro>());
		}  
	}
	
	private void criarSessaoCliente(HttpServletRequest request, Cliente cliente) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("sessaoCliente", cliente); 
	}
	
	public ArrayList<Livro> getCarrinhoSessao(HttpServletRequest request, String atributoSessoa) {
		HttpSession session = request.getSession();
		ArrayList<Livro> carrinhoSessao = (ArrayList<Livro>) session.getAttribute(atributoSessoa);
		
		return carrinhoSessao;
	}
	
	private Cliente getClienteSessao(HttpServletRequest request, String atributoSessao) {
		HttpSession session = request.getSession();
		Cliente clienteSessao = (Cliente) session.getAttribute(atributoSessao);
		
		return clienteSessao;
	}
	
	public void finalizarPedidoCliente(HttpServletRequest request, Cliente cliente) {
		
		ArrayList<Livro> carrinhoCliente = getCarrinhoSessao(request, "sessaoCarrinho");

		// Salvar e retornar o cliente - verificar se o cliente existe
		Cliente clientePedido = clienteDAO.buscarCliente(cliente.getEmail());
		
		if(clientePedido == null) {
			clientePedido = clienteDAO.salvarCliente(cliente);
		} 
		
		// Criar, salvar e retornar o Pedido 
		Pedido pedido = salvarPedidoCliente(carrinhoCliente, clientePedido);
		
		// Salvar os itens do pedido com os dados do pedido e cliente
		ArrayList<ItensPedido> carrinhoPersistencia = new ArrayList<ItensPedido>();
		buildItensPedido(carrinhoCliente, pedido, carrinhoPersistencia);
		salvarCarrinhoCompras(carrinhoPersistencia); // Salvar Itens no banco de dados
		
		criarSessaoCliente(request, clientePedido);
		
		// Apaga o carrinho da sessão do usuário
		limparCarrinhoComprasCliente(request);
	}

	private void limparCarrinhoComprasCliente(HttpServletRequest request) {
		request.getSession().setAttribute("sessaoCarrinho", new ArrayList<Livro>());
	}

	private void salvarCarrinhoCompras(
			ArrayList<ItensPedido> carrinhoPersistencia) {
		for (ItensPedido itemPedido : carrinhoPersistencia) {
			itensPedidoDAO.salvarItensPedido(itemPedido);
		}
	}

	private void buildItensPedido(ArrayList<Livro> carrinhoCliente,
			Pedido pedido, ArrayList<ItensPedido> carrinhoPersistencia) {
		for (Livro livro : carrinhoCliente) {
			
			ItensPedido itensPedido = new ItensPedido();
			itensPedido.setLivro(livro);
			itensPedido.setPedido(pedido);
			itensPedido.setPrecoUnitario(livro.getPreco());
			itensPedido.setQuantidade(livro.getQuantidade());
			itensPedido.setValorTotalItem(livro.getTotal());
			
			carrinhoPersistencia.add(itensPedido);
		}
	}

	private Pedido salvarPedidoCliente(ArrayList<Livro> carrinhoCliente,
			Cliente clientePedido) {
		
		Pedido pedido = new Pedido();
		pedido.setCliente(clientePedido);
		pedido.setDataPedido(Calendar.getInstance());
		pedido.setValorTotal(calcularTotalPedido(carrinhoCliente));
		pedido = pedidoDAO.salvarPedido(pedido);
		
		return pedido;
	}
	
	private BigDecimal calcularTotalPedido(ArrayList<Livro> carrinhoCliente) {
		BigDecimal valorTotalPedido = new BigDecimal(0);
		
		for (Livro livro : carrinhoCliente) {
			valorTotalPedido = valorTotalPedido.add(livro.getTotal());
		}
		
		return valorTotalPedido;
	}

	public ArrayList<Pedido> buscarPedidosClienteSessao(HttpServletRequest request) {
		
		ArrayList<Pedido> listaPedidos = null;
		
		// Verifica se o cliente esta gravado na sessão
		Cliente clienteSessao = getClienteSessao(request, "sessaoCliente");
		
		if(clienteSessao == null) {
			return null;
		}
		
		return buildPedidosCliente(clienteSessao);
	}
	
	public ArrayList<Pedido> buscarPedidosCliente(String emailCliente) {
		
		ArrayList<Pedido> listaPedidos = null;
		
		// Verifica se esta cadastrado no banco
		Cliente cliente = clienteDAO.buscarCliente(emailCliente);
		
		if(cliente == null) {
			return null;
		}
		
		return buildPedidosCliente(cliente);
	}
	
	public List<ItensPedido> buscarItensPedido(Pedido pedido) {
		
		return itensPedidoDAO.getListItensPedido(pedido.getId());
	}
	
	private ArrayList<Pedido> buildPedidosCliente(Cliente cliente) {
		ArrayList<Pedido> listaPedidos = null;
		
		// Busca os pedidos do cliente 
		listaPedidos = (ArrayList<Pedido>) pedidoDAO.getListPedidos(cliente.getId());
		
		// Caso não encontrar nada, retorna nulo
		return listaPedidos;
	}
	
}
