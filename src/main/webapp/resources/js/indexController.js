var app = angular.module('app-betha-book', []);

app.value("urlBaseRest", "http://localhost:8080/BethaBookStore/rest/");


app.service("indexService", function($http, urlBaseRest) {
	
	var listaLivros = null;
	
	this.buscarListaProduto = function(callbackSuccess, callbackErro) {
		$http.get(urlBaseRest + "livros/").success(callbackSuccess).error(callbackErro);
	};
	
	this.getCarrinhoSessao = function(callbackSuccess, callbackErro) {
		$http.get(urlBaseRest + "livros/carrinhoSessao/").success(callbackSuccess).error(callbackErro);
	};
});


app.controller('indexController', function($scope, $http, urlBaseRest, 
		indexService) {
	
	$scope.livros = [];
	$scope.carrinho = [];
	$scope.pedidos = [];
	$scope.itensPedido = [];
	$scope.viewIndex = false; 
	$scope.viewCarrinho = false;
	$scope.viewDetalhe = false;
	$scope.viewPedidos = false;
	$scope.viewEmailConfirmPedidos = false;
	$scope.totalProdutos = 0;
	
	$scope.getListaLivros = function() {
		
		indexService.buscarListaProduto(function(data){
			$scope.livros = data;
			$scope.showProdutos();
			$scope.obterCarrinho();
		}, function(erro){
			$scope.showMensagemErro('Erro na chamada da lista de Livros');
		});
	};
	
	$scope.obterCarrinho = function() {
		indexService.getCarrinhoSessao(function(data){
			$scope.carrinho = data;
			$scope.calcularTotalProdutos();
		}, function(erro){
			$scope.showMensagemErro('Erro na chamada do carrinho');
		});
	};
	
	$scope.showProdutos = function() {
		$scope.viewIndex = true;
		$scope.viewCarrinho = false;
		$scope.viewDetalhe = false;
		$scope.viewPedidos = false;
	}
	
	$scope.showViewCarrinho = function() {
		$scope.viewCarrinho = true;
		$scope.viewIndex = false;
		$scope.viewDetalhe = false;
		$scope.viewPedidos = false;
	}
	
	$scope.getDetalheLivro = function(livro) {
		$scope.viewIndex = !$scope.viewIndex
		$scope.viewDetalhe = !$scope.viewDetalhe;
		$scope.viewCarrinho = false;
		$scope.livroDetalheSelecionado = livro;
	}
	
	$scope.showViewPedido = function(){
		$scope.viewPedidos = true;
		$scope.viewIndex = false;
		$scope.viewDetalhe = false;
		$scope.viewCarrinho = false;
	}
	
	$scope.calcularTotalProdutos = function() {
		if($scope.carrinho.length != 0) {
			$scope.totalProdutos = 0;
			angular.forEach($scope.carrinho, function(item, key) {
				$scope.totalProdutos += item.total;
			});
		} else {
			$scope.totalProdutos = 0;
		}
	};
	
	
	$scope.addCarrinho = function(livro) {
		var livroNoCarrinho = $scope.verificarItemCarrinho(livro);
		
		if(livroNoCarrinho == false) {
			livro.total = livro.preco;
			livro.quantidade = 1;
			$scope.carrinho.push(livro);
			$scope.calcularTotalQtd(livro);
		}
		
		$scope.showViewCarrinho();
		$scope.buildCarrinhoSessao();
	};
	
	$scope.verificarItemCarrinho = function(livro) {
		var existeItemCarrinho = false;
		
		angular.forEach($scope.carrinho, function(item, key) {
			if(item.titulo === livro.titulo) {
				existeItemCarrinho = true;
				item.quantidade = item.quantidade + 1;
				$scope.calcularTotalQtd(item);
			}
		});
		
		return existeItemCarrinho;
	};
	
	$scope.buildCarrinhoSessao = function() {
		
		$http({
			method: "PUT",
			url: urlBaseRest + "livros/addCarrinho/",
			data: $scope.carrinho
		}).then(function successCallback(response) {
			console.log("###Carrinho enviado para o servidor");
		}, function errorCallback(reason) {
			alert("Erro na chamado do carrinho");
		});
		
	}
	
	$scope.finalizarPedido = function() {
		$http({
			method: "POST",
			url: urlBaseRest + "livros/finalizarPedido/",
			data: $scope.cliente
		}).then(function successCallback(response) {
			$scope.getListaLivros();
			$scope.limparFormCliente();
			$scope.mensagemPedidoFinalizado = response.data;
		}, function errorCallback(reason) {
			alert("Erro ao finalizar o carrinho");
		});
		
		$scope.fecharModal = 'modal';
	};
	
	
	$scope.getPedidosSessao = function() {
		
		$http({
			method: "GET",
			url: urlBaseRest + "livros/pedidosClienteSessao/",
		}).then(function successCallback(response) {
			
			if(response.data != ''){
				$scope.pedidos = response.data;
				$scope.showViewPedido();
			} else {
				$scope.viewEmailConfirmPedidos = true;
			}
			
		}, function errorCallback(reason) {
			alert("Erro ao obter os pedidos");
		});
	}
	
	$scope.getPedidosClienteEmail = function(){
		
		$http({
			method: "GET",
			url: urlBaseRest + "livros/pedidosCliente/" + $scope.emailCliente + "/",
		}).then(function successCallback(response) {
			
			if(response.data != '') {
				$scope.pedidos = response.data;
				$scope.showViewPedido();
			} else {
				alert('Não existe pedidos para esta e-mail.');
			}
			
		}, function errorCallback(reason) {
			alert("Erro ao obter os pedidos");
		});
		
		$scope.emailCliente = '';
		$scope.viewEmailConfirmPedidos = false;
	};
	
	$scope.getDetalhePedido = function(pedido) {
		$http({
			method: "POST",
			url: urlBaseRest + "livros/detalhePedido/",
			data: pedido
		}).then(function successCallback(response) {
			$scope.itensPedido = response.data;
		}, function errorCallback(reason) {
			alert("Erro ao finalizar o carrinho");
		});
	}
	
	$scope.calcularTotalQtd = function(livroCarrinho) {
		
		if (livroCarrinho.quantidade != null || livroCarrinho.quantidade != undefined) {
			livroCarrinho.total = 0;
			livroCarrinho.total = livroCarrinho.preco * livroCarrinho.quantidade;
		}
		
		$scope.calcularTotalProdutos();
		$scope.buildCarrinhoSessao();
	}
	
	$scope.removerItemCarrinho = function(livroRemove) {
		var index = $scope.getIndexItemCarrinho(livroRemove);
		
		if(index != -1){
			$scope.carrinho.splice(index, 1);
		}
		
		if($scope.carrinho.length == 0) {
			$scope.getListaLivros();
		}
		
		$scope.calcularTotalProdutos();
		$scope.buildCarrinhoSessao();
	};
	
	$scope.fecharMensagemPedidoFinalizado = function() {
		$scope.mensagemPedidoFinalizado =  null;
	}
	
	$scope.getIndexItemCarrinho = function(livro) {
		var index = $scope.carrinho.indexOf(livro);
		
		return index;
	}
	
	$scope.limparFormCliente = function() {
		$scope.cliente = {};
	}

	$scope.showMensagemErro = function(mensagem) {
		alert(mensagem);
	};

	$scope.inicializar = function() {
		$scope.getListaLivros();
	};

	$scope.inicializar();
});