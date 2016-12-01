package br.com.model.modelo;

public enum Categoria {

	HATCH_COMPACTO("Hatch Compacto"),
	HATCH_MEDIO("Hatch Médio"),
	SEDAN_COMPACTO("Sedan Compacto"),
	SEDAN_MEIDO("Sedan Médio"),
	SEDAN_GRANDE("Sedan Grande"),
	MINIVAN("Minivan"),
	ESPORTIVO("Esportivo"),
	UTILITARIO_COMERCIAL("Utilitário Comercial");
	
	private String descricao;
	
	Categoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}
