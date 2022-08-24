package br.com.alura.microservice.loja.controller.dto;

public class ItemDaCompraDTO {
	
	private long id;
	
	private long quantidade;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}
}
