package br.com.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.model.modelo.Aluguel;
import br.com.model.modelo.Carro;
import br.com.model.modelo.util.TotalDeAlugueisPorCarro;
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

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Carro> buscarCarrosNuncaAlugados() {
		Session session = this.entityManager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(Carro.class); 
		/*
		 * select * 
  			from carro
  			where codigo not in (
  				select codigo_carro from aluguel 
  					where codigo_carro is not null)
		 */
	    
	    DetachedCriteria criteriaAluguel = DetachedCriteria.forClass(Aluguel.class)
	    	.setProjection(Projections.property("carro"))
	    	.add(Restrictions.isNotNull("carro"));
	    
	    criteria.add(Property.forName("codigo").notIn(criteriaAluguel));
	    
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TotalDeAlugueisPorCarro> buscarTotalAlugueisPorCarro() {
		Session session = this.entityManager.unwrap(Session.class);
	    Criteria criteria = session.createCriteria(Carro.class); 
	    
	    criteria.createAlias("alugueis", "a");
	    
	    ProjectionList pl = Projections.projectionList()
	    					.add(Projections.groupProperty("placa").as("placa"))
	    					.add(Projections.count("a.codigo").as("totalDeAlugueis"));
	    criteria.setProjection(pl)
	    		.addOrder(Order.desc("totalDeAlugueis"))
	    		.setResultTransformer(Transformers.aliasToBean(TotalDeAlugueisPorCarro.class));
	    
		return criteria.list();
	}

	
	
}
