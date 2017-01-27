package com.devgomes.bethaBookStore.dao;

import java.util.List;

import javax.enterprise.inject.Produces;

import com.devgomes.bethaBookStore.model.Livro;

public class LivroDAO extends DefaultDAO {
	
	public LivroDAO() {
		super();
	}
	
	public Long salvarLivro(Livro livro) {
		livro = (Livro) salvar(livro);
		
		return livro.getId();
	}
	
	public List<Livro> getListaLivros() {
		return getSession()
				.createNamedQuery("Livro.selecionarTodos", Livro.class)
				.getResultList();
	}
	
	public Livro getLivroPorId(Long id) {
		return getSession()
				.find(Livro.class, id);
	}
	
	public void deletarLivro(Livro livro) {
		deletar(livro);
	}
	
}
