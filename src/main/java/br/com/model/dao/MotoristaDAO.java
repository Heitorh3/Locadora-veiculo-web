package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.model.modelo.Motorista;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class MotoristaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Motorista motorista){
		this.entityManager.merge(motorista);
	}

	@SuppressWarnings("unchecked")
	public List<Motorista> buscarTodos() {
		return entityManager.createQuery("from Motorista").getResultList();
	}

	@Transacional
	public void excluir(Motorista motorista) throws NegocioException {
		motorista = entityManager.find(Motorista.class, motorista.getCodigo());
		
		try{
			entityManager.remove(motorista);
			entityManager.flush();
		}catch(PersistenceException e){
			throw new NegocioException("Motorista não pode ser excluido");
		}
		
	}

	public Motorista buscarPeloCodigo(Long codigo) {
		return entityManager.find(Motorista.class, codigo);
	}
	
}
