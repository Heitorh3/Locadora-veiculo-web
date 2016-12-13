package br.com.model.modelo.util;

import java.io.Serializable;

public class TotalDeAlugueisPorCarro implements Serializable {

	private static final long serialVersionUID = 1L;

	private String placa;
	private Long totalDeAlugueis;

	public TotalDeAlugueisPorCarro() {
	}
	
	public TotalDeAlugueisPorCarro(String placa, Long totalDeAlugueis) {
		super();
		this.placa = placa;
		this.totalDeAlugueis = totalDeAlugueis;
	}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public Long getTotalDeAlugueis() {
		return totalDeAlugueis;
	}
	public void setTotalDeAlugueis(Long totalDeAlugueis) {
		this.totalDeAlugueis = totalDeAlugueis;
	}
	
}
