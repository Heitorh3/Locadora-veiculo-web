package br.com.model.dao;


import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.Aluguel;


public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void salvar(Aluguel aluguel) {
		manager.merge(aluguel);
	}

}