package com.devgomes.bethaBookStore.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@NamedQueries({
	@NamedQuery(name = "Livro.selecionarTodos", query = "select l from Livro l")
})
@Entity
public class Livro implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 80, nullable = false)
	private String titulo;

	@Column(length = 2048, nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private Integer paginas;
	
	@Column(length = 50, nullable = false)
	private String autor;
	
	@Column(length = 50)
	private String editora;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	private Integer ano;
	
	@Column(name="url_capa_livro", length = 2048)
	private String urlCapaLivro;
	
	@Transient
	private Integer quantidade;
	
	@Transient
	private BigDecimal total;
	
	
	public Livro() { }
	
	public Livro(Long id, String titulo, String descricao, Integer paginas,
			String autor, String editora, BigDecimal preco, Integer ano, String urlCapaLivro) {
		
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.paginas = paginas;
		this.autor = autor;
		this.editora = editora;
		this.preco = preco;
		this.ano = ano;
		this.urlCapaLivro = urlCapaLivro;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getPaginas() {
		return paginas;
	}
	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getEditora() {
		return editora;
	}
	public void setEditora(String editora) {
		this.editora = editora;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getUrlCapaLivro() {
		return urlCapaLivro;
	}

	public void setUrlCapaLivro(String urlCapaLivro) {
		this.urlCapaLivro = urlCapaLivro;
	}
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", descricao="
				+ descricao + ", paginas=" + paginas + ", autor=" + autor
				+ ", editora=" + editora + ", preco=" + preco + "]";
	}
	
}
