package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.model.modelo.Funcionario;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class FuncionarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Funcionario funcionario){
		this.entityManager.merge(funcionario);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> buscarTodos() {
		return entityManager.createQuery("from Funcionario").getResultList();
	}

	@Transacional
	public void excluir(Funcionario funcionario) throws NegocioException {
		funcionario = entityManager.find(Funcionario.class, funcionario.getCodigo());
		
		try{
			entityManager.remove(funcionario);
			entityManager.flush();
		}catch(PersistenceException e){
			throw new NegocioException("Funcionario não pode ser excluido");
		}
		
	}

	public Funcionario buscarPeloCodigo(Long codigo) {
		return entityManager.find(Funcionario.class, codigo);
	}
	
}
