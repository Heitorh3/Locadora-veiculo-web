package br.com.model.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.Fabricante;

public class FabricanteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Fabricante fabricante){
		this.entityManager.persist(fabricante);
	}
}
