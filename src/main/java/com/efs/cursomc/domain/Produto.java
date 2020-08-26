package com.efs.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5840072632476504286L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	//ajustar para nao ocorrer referencia ciclica --> avisa que já foi buscado no outro relacionamento
	@JsonBackReference
	//mapeamento muitos para muitos
	@ManyToMany
	//criando a tabela intermediaria 
	@JoinTable(
		//nome da tabela
		name = "PRODUTO_CATEGORIA",
		//FK do relacionamento com a tabela desta classe (Produto)
		joinColumns = @JoinColumn(name = "produto_id"),
		//FK do relacionamento com a outra tabela (categoria)
		inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	@JsonIgnore
	//mapeado por quem?
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();
	
	/**
	 * 
	 */
	public Produto() {
	}
	
	public Produto(Integer id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	@JsonIgnore
	public List<Pedido> getPedidos() {
		 List<Pedido> lista = new ArrayList<Pedido>();
		for (ItemPedido itemPedido: itens) {
			lista.add(itemPedido.getPedido());
		}
		return lista;
	}

	public Produto(String nome, Double preco) {
		this(null, nome, preco);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		if (!(obj instanceof Produto))
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}