package br.com.model.filter;

import java.io.Serializable;
import java.util.Date;

import br.com.model.modelo.Carro;

public class FiltroAluguel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date dataEntrega;
	private Date dataDevolucao;
	private Carro carro;

	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
}
