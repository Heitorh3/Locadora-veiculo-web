package br.com.model.modelo;

public enum Sexo {

	MASCULINO("Masculino"),
	Feminino("Feminino");
	
	private String descricao;
	
	Sexo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}
