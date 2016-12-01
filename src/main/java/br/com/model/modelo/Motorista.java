package br.com.model.modelo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOTORISTA")
public class Motorista extends Pessoa{

	private String numeroCNH;

	@Column(name = "numero_cnh")
	public String getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}

}
