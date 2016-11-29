package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.Acessorio;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class AcessorioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Acessorio acessorio){
		this.entityManager.merge(acessorio);
	}

	@SuppressWarnings("unchecked")
	public List<Acessorio> buscarTodos() {
		return entityManager.createQuery("from Acessorio").getResultList();
	}

	@Transacional
	public void excluir(Acessorio acessorio) throws NegocioException {
		acessorio = entityManager.find(Acessorio.class, acessorio.getCodigo());
		
		entityManager.remove(acessorio);
	}

	public Acessorio buscarPeloCodigo(Long codigo) {
		return entityManager.find(Acessorio.class, codigo);
	}
}
