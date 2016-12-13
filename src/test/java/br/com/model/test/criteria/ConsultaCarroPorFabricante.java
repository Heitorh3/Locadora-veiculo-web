package br.com.model.test.criteria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.model.modelo.Carro;

public class ConsultaCarroPorFabricante {

private static EntityManagerFactory factory;
	
	private EntityManager manager;
	
	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("locadoraPU");
	}
	
	@Before
	public void setUp() {
		this.manager = factory.createEntityManager();
	}
	
	@After
	public void tearDown() {
		this.manager.close();
	}
	
	@Test
	public void buscaCarrosDoFabricante() {
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Carro.class);
		
		String fabricante = "Bm";
		
		criteria.createAlias("modelo", "m")
				.createAlias("m.fabricante", "f")
				.add(Restrictions.ilike("f.nome", fabricante, MatchMode.ANYWHERE));
				
		
		@SuppressWarnings("unchecked")
		List<Carro> carros = criteria.list();
		
		carros.forEach(c -> System.out.printf("Placa: %s\n",c.getPlaca()));
		
	}
	
}





