package br.com.model.test.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.model.modelo.Aluguel;
import br.com.model.modelo.Carro;

public class ExemplosCriteria {

	private static EntityManagerFactory factory;
	
	private EntityManager entityManager;
	
	@BeforeClass
	public static void init(){
		factory = Persistence.createEntityManagerFactory("locadoraPU");
	}
	
	@Before
	public void setUp(){
		this.entityManager = factory.createEntityManager();
	}
	
	@After
	public void tearDown(){
		this.entityManager.close();
	}
	
	@Test
	public void projecoes(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		
		Root<Carro> carro= criteriaQuery.from(Carro.class);
		criteriaQuery.select(carro.<String>get("placa"));
		
		TypedQuery<String> query = entityManager.createQuery(criteriaQuery);
		List<String> placas = query.getResultList();
		
		placas.forEach(p -> System.out.println(p));
		
	}
	
	@Test
	public void funcoesDeAgregacao(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
		
		Root<Aluguel> alguel = criteriaQuery.from(Aluguel.class);
		criteriaQuery.select(builder.sum(alguel.<BigDecimal>get("valorTotal")));
		
		TypedQuery<BigDecimal> query = entityManager.createQuery(criteriaQuery);
		
		BigDecimal total = query.getSingleResult();
		
		System.out.println("Soma de todos os alugueis: " + total);
	}
	
}
