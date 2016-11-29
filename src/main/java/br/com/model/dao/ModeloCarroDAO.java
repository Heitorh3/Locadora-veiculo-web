package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.ModeloCarro;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class ModeloCarroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(ModeloCarro modeloCarro){
		this.entityManager.merge(modeloCarro);
	}

	@SuppressWarnings("unchecked")
	public List<ModeloCarro> buscarTodos() {
		return entityManager.createQuery("from ModeloCarro").getResultList();
	}

	@Transacional
	public void excluir(ModeloCarro modeloCarro) throws NegocioException {
		modeloCarro = entityManager.find(ModeloCarro.class, modeloCarro.getCodigo());
		
		entityManager.remove(modeloCarro);
	}

	public ModeloCarro buscarPeloCodigo(Long codigo) {
		return entityManager.find(ModeloCarro.class, codigo);
	}
}
