package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.model.dao.MotoristaDAO;
import br.com.model.modelo.Motorista;
import br.com.model.util.jpa.Transacional;

public class CadastroMotoristaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MotoristaDAO motoristaDAO;
	
	@Transacional
	public void salvar(Motorista motorista) throws NegocioException {
		this.motoristaDAO.salvar(motorista);
	}

}