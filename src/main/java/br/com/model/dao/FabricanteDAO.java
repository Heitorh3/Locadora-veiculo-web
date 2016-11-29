package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.Fabricante;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class FabricanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Fabricante fabricante){
		this.entityManager.persist(fabricante);
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarTodos() {
		return entityManager.createQuery("from Fabricante").getResultList();
	}

	@Transacional
	public void excluir(Fabricante fabricante) throws NegocioException {
		fabricante = entityManager.find(Fabricante.class, fabricante.getCodigo());
		
		entityManager.remove(fabricante);
	}
}
