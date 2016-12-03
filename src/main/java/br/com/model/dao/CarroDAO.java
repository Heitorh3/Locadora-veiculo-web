package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.model.modelo.Carro;
import br.com.model.service.NegocioException;
import br.com.model.util.jpa.Transacional;

public class CarroDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public void salvar(Carro carro){
		this.entityManager.merge(carro);
	}

	@SuppressWarnings("unchecked")
	public List<Carro> buscarTodos() {
		return entityManager.createNamedQuery("Carro.buscarTodos").getResultList();
	}

	@Transacional
	public void excluir(Carro carro) throws NegocioException {
		carro = entityManager.find(Carro.class, carro.getCodigo());
		
		entityManager.remove(carro);
	}

	public Carro buscarPeloCodigo(Long codigo) {
		return entityManager.find(Carro.class, codigo);
	}
	
	public Carro buscarCarroComAcessorios(Long codigo){
		return entityManager.createNamedQuery("Carro.buscarCarroComAcessorios", Carro.class)
				.setParameter("codigo", codigo)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Carro> buscaComPaginacao(int first, int pageSize) {
		return entityManager.createNamedQuery("Carro.buscarTodos")
				.setFirstResult(first)
				.setMaxResults(pageSize)
				.getResultList();
	}

	public Long encontrarQuantidadeDeCarros() {
		return entityManager.createQuery("select count(c) from Carro c", Long.class).getSingleResult();
	}
}
