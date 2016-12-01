package br.com.model.modelo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOTORISTA")
public class Motorista extends Pessoa{

	private String numeroCHN;

	@Column(name = "numero_cnh")
	public String getNumeroCHN() {
		return numeroCHN;
	}

	public void setNumeroCHN(String numeroCHN) {
		this.numeroCHN = numeroCHN;
	}
	
}
