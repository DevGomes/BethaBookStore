
var app = angular.module('app-betha-book', []);

app.value("urlBase", "http://localhost:8080/BethaBookStore/rest/");

app.controller('LivroController', function($scope, $http, urlBase){
	
	$scope.livros = [];
	$scope.livroEdit = {};
	$scope.tituloFormLivro = '';
	$scope.fecharModal = '';
	
	$scope.getListaLivros = function() {
		
		$http({
			method: 'GET',
			url: urlBase + "livros/",
		}).then(function successCallback(response){
			$scope.livros = response.data;
		}, function errorCallback(response){
			$scope.showMensagemErro('Erro na chamada da lista de Livros');
		});
	};
	
	$scope.editarLivro = function(livro) {
		$scope.tituloFormLivro = 'Editar Livro';
		$scope.livroEdit = angular.copy(livro);
	};
	
	$scope.novoLivro = function() {
		$scope.tituloFormLivro = 'Novo Livro';
		$scope.livroEdit = {};
	};
	
	$scope.salvarLivro = function() {
		
		$http({
			method: "POST",
			url: urlBase + "livros/salvar/",
			data: $scope.livroEdit
		}).then(function successCallback(response) {
			$scope.getListaLivros();
		}, function errorCallback(reason) {
			alert('Erro ao salvar livro');
		});
		
		$scope.fecharModal = 'modal';
	};
	
	$scope.setLivroDelecao = function(livro) {
		$scope.livroDelecao = livro;
	};
	
	$scope.deletarLivro = function() {
		
		$http({
			method: "DELETE",
			url: urlBase + "livros/deletar/" + $scope.livroDelecao.id + "/"
		}).then(function successCallback(reponse){
			$scope.getListaLivros();
		},function errorCallback(reason){
			alert('Erro ao deletar livro');
		});
		
		$scope.fecharModal = 'modal';
	};
	
	$scope.showMensagemErro = function(mensagem) {
		alert(mensagem);
	};
	
	$scope.inicializar = function() {
		$scope.getListaLivros();
	};
	
	$scope.inicializar();
	
});