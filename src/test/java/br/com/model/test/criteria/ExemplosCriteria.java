package br.com.model.test.criteria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.model.modelo.Aluguel;
import br.com.model.modelo.ApoliceSeguro;
import br.com.model.modelo.Carro;
import br.com.model.modelo.Carro_;
import br.com.model.modelo.ModeloCarro;
import br.com.model.modelo.ModeloCarro_;
import br.com.model.modelo.Motorista;

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
	
	@Test
	public void resultadosComplexos(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa"), carro.get("valorDiaria"));
		
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
		List<Object[]> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println("Placa: " + r[0] + " Valores: " + r[1]));
	}
	
	@Test
	public void resultadoTupla(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createTupleQuery();
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.multiselect(carro.get("placa").alias("placaDoCarro"),
								  carro.get("valorDiaria").alias("valorDaDiaria"));
		
		TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
		List<Tuple> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println("Placa " + r.get("placaDoCarro")
												 +" Valor da diaria: " + r.get("valorDaDiaria")));
	}
	
	@Test
	public void resultadoConstrutores(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PrecoCarro> criteriaQuery = builder.createQuery(PrecoCarro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(builder.construct(PrecoCarro.class,carro.get("placa"), carro.get("valorDiaria")));
		
		TypedQuery<PrecoCarro> query = entityManager.createQuery(criteriaQuery);
		List<PrecoCarro> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println("Placa " + r.getPlaca()
		 + " Valor da diaria: " + r.getValor()));
	}
	
	@Test
	public void exemploFuncoes(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Predicate predicate = builder.equal(builder.upper(carro.<String>get("cor")), "Prata".toUpperCase());
		
		criteriaQuery.select(carro);
		criteriaQuery.where(predicate);
		
		TypedQuery<Carro> query = entityManager.createQuery(criteriaQuery);
		List<Carro> resultado = query.getResultList();
		
		
		resultado.forEach(r -> System.out.println(r.getCor() + " - " + r.getPlaca()));
	}
	
	@Test
	public void exemploOrdenacao(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Order order = builder.desc(carro.get("valorDiaria"));
		
		criteriaQuery.select(carro);
		criteriaQuery.orderBy(order);
		
		TypedQuery<Carro> query = entityManager.createQuery(criteriaQuery);
		List<Carro> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println(r.getValorDiaria()));
		
	}
	
	@Test
	public void exemploFetchJoin(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join)carro.fetch("modelo");
		
		criteriaQuery.select(carro);
		
		TypedQuery<Carro> query = entityManager.createQuery(criteriaQuery);
		List<Carro> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println(r.getPlaca() + " - " + r.getModelo().getDescricao()));
	}
	
	@Test		//Subqueries
	public void mediaDasDiariasDosCarros(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		criteriaQuery.select(builder.avg(carro.<Double>get("valorDiaria")));
		
		TypedQuery<Double> query = entityManager.createQuery(criteriaQuery);
		Double resultado = query.getSingleResult();
		
		System.out.println("Média " + resultado);
	}
	
	@Test
	public void carrosComValoresAcimaDaMedia(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Root<Carro> carroSubQuery = subquery.from(Carro.class);
		
		subquery.select(builder.avg(carro.<Double>get("valorDiaria")));
		
		criteriaQuery.select(carro);
		criteriaQuery.where(builder.greaterThanOrEqualTo(carro.get("valorDiaria"), subquery));
		
		TypedQuery<Carro> query = entityManager.createQuery(criteriaQuery);
		List<Carro> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println(r.getPlaca() + " - " + r.getValorDiaria()));
	}
	
	@Test
	public void exemploMetamodel(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carro> criteriaQuery = builder.createQuery(Carro.class);
		
		Root<Carro> carro = criteriaQuery.from(Carro.class);
		Join<Carro, ModeloCarro> modelo = (Join)carro.fetch(Carro_.modelo);
		
		criteriaQuery.select(carro);
		criteriaQuery.where(builder.equal(modelo.get(ModeloCarro_.descricao), "Civic"));
		
		TypedQuery<Carro> query = entityManager.createQuery(criteriaQuery);
		List<Carro> resultado = query.getResultList();
		
		resultado.forEach(r -> System.out.println(r.getPlaca() + " - " + r.getModelo().getDescricao()));
		
	}
	
	@Test
	public void motoristasQueMaisAlugaramCarrosNoUltimoMes() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = builder.createQuery(Tuple.class);
		
		Root<Aluguel> aluguel = criteriaQuery.from(Aluguel.class);
		
		Expression<Integer> month = builder.function("month", Integer.class, aluguel.<Calendar>get("dataPedido"));
		Expression<Integer> year = builder.function("year", Integer.class, aluguel.<Calendar>get("dataPedido"));

		Expression<Integer> monthToday = builder.function("month", Integer.class, builder.currentDate());
		Expression<Integer> yearToday = builder.function("year", Integer.class, builder.currentDate());
	
		criteriaQuery.multiselect(aluguel.get("motorista").alias("motorista"), builder.count(aluguel).alias("aluguel"));
		criteriaQuery.where(builder.equal(month, monthToday), builder.equal(year, yearToday));

		criteriaQuery.groupBy(aluguel.get("motorista"));

		TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
		query.setMaxResults(1);
		List<Tuple> tuples = query.getResultList();
		
		tuples.forEach(t -> System.out.println("Motorista: " + ((Motorista)t.get("motorista")).getNome() + " - " 
															 +  "Aluguéis: " + t.get("aluguel")));
	}
	
	@Test
	public void aluguelComMaiorValor(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);
		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
				
		Root<Aluguel> aluguel = criteriaQuery.from(Aluguel.class);
		Root<Aluguel> aluguelSubQuery = subquery.from(Aluguel.class);
		
		Join<Aluguel, ApoliceSeguro> seguro = (Join)aluguel.fetch("apoliceSeguro");
		Join<Aluguel, Motorista> motorista = (Join)aluguel.fetch("motorista");
		Join<Aluguel, Carro> carro = (Join)aluguel.fetch("carro");
		
		subquery.select(builder.max(aluguelSubQuery.<Double>get("valorTotal")));
		
		criteriaQuery.select(aluguel);
		criteriaQuery.where(builder.equal(aluguel.get("valorTotal"), subquery));
		
		TypedQuery<Aluguel> query = entityManager.createQuery(criteriaQuery);
		
		Aluguel resultado = query.getSingleResult();
		
		System.out.println("Maior Aluguel: " + resultado.getValorTotal());
		
	}
	
	@Test
	public void maiorAluguelDoMes(){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);
		Subquery<Double> subquery = criteriaQuery.subquery(Double.class);
		
		Root<Aluguel> aluguel = criteriaQuery.from(Aluguel.class);
		Root<Aluguel> aluguelSubQuery = subquery.from(Aluguel.class);
		
		List<Predicate> predicates = new ArrayList<>();
		ParameterExpression<Date> dataInicial = builder.parameter(Date.class, "dataInicial");
		ParameterExpression<Date> dataFinal = builder.parameter(Date.class, "dataFinal");
		predicates.add(builder.between(aluguelSubQuery.get("dataPedido"), dataInicial, dataFinal));
		
		subquery.select(builder.max(aluguelSubQuery.<Double>get("valorTotal")));
		subquery.where(predicates.toArray(new Predicate[0]));
		
		criteriaQuery.select(aluguel);
		criteriaQuery.where(builder.equal(aluguel.<Double>get("valorTotal"), subquery));
		
		
		TypedQuery<Aluguel> query = entityManager.createQuery(criteriaQuery);
		
		Calendar finalDate = Calendar.getInstance();
				 finalDate.set(2016, 12,  07); 
				 
		Calendar initialDate = Calendar.getInstance();
				 initialDate.set(2016, 12,  01); 
		
		query.setParameter("dataInicial", initialDate.getTime(), TemporalType.DATE);
		query.setParameter("dataFinal", finalDate.getTime(),TemporalType.DATE);
		
		Aluguel resultado = query.getSingleResult();
		
		System.out.println( initialDate.getTime());
		
		System.out.println("Maior Aluguel do mês: " + resultado.getValorTotal());
		
	}
}
