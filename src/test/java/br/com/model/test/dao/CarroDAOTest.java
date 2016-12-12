package br.com.model.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jintegrity.core.JIntegrity;
import com.jintegrity.helper.JPAHelper;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.Carro;

public class CarroDAOTest {

	private JIntegrity helper = new JIntegrity();
	
	private CarroDAO carroDAO;
	
	@Before
	public void init() {
		helper.insert();
		
		this.carroDAO = new CarroDAO();
		this.carroDAO.setEntityManager(JPAHelper.currentEntityManager());
	}
	
	@Test
	public void buscarCarroPeloCodigo() {
		Carro carro = this.carroDAO.buscarPeloCodigo(8L);
		
		assertEquals(8L, carro.getCodigo().longValue());
		assertEquals("JJA-1111", carro.getPlaca());
	}
	
}
