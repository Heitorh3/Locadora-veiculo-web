package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.Carro;
import br.com.model.util.jpa.Transacional;

public class CadastroCarroService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CarroDAO carroDAO;
	
	@Transacional
	public void salvar(Carro carro) throws NegocioException {
		/*
		if (carro.getPlaca() == null || carro.getPlaca().trim().equals("")) {
			throw new NegocioException("A placa é obrigatória");
		}
		*/
		this.carroDAO.salvar(carro);
	}

}