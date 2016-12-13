package br.com.model.dao;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

import br.com.model.filter.FiltroAluguel;
import br.com.model.modelo.Aluguel;
import br.com.model.modelo.ModeloCarro;


public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void salvar(Aluguel aluguel) {
		manager.merge(aluguel);
	}

	/*
	 * Consulta utilizando a especificação JPA
	 */
	public List<Aluguel> buscarPorDataDeEntregaEModeloCarro(FiltroAluguel filtroAluguel) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);
		Root<Aluguel> a = criteriaQuery.from(Aluguel.class);
		criteriaQuery.select(a);
		
		List<Predicate> predicates = new ArrayList<>();
		if (filtroAluguel.getDataEntrega() != null) {
			ParameterExpression<Date> dataEntregaInicial = builder.parameter(Date.class, "dataEntregaInicial");
			ParameterExpression<Date> dataEntregaFinal = builder.parameter(Date.class, "dataEntregaFinal");
			predicates.add(builder.between(a.<Date>get("dataEntrega"), dataEntregaInicial, dataEntregaFinal));
		}
		
		if (filtroAluguel.getDataDevolucao() != null) {
			ParameterExpression<Date> dataDevolucaoInicial = builder.parameter(Date.class, "dataDevolucaoInicial");
			ParameterExpression<Date> dataDevolucaoFinal = builder.parameter(Date.class, "dataDevolucaoFinal");
			predicates.add(builder.between(a.<Date>get("dataDevolucao"), dataDevolucaoInicial, dataDevolucaoFinal));
		}
		
		if (filtroAluguel.getCarro().getModelo() != null) {
			ParameterExpression<ModeloCarro> modeloExpression = builder.parameter(ModeloCarro.class, "modelo");
			predicates.add(builder.equal(a.get("carro").get("modelo"), modeloExpression));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Aluguel> query = manager.createQuery(criteriaQuery);
		
		if (filtroAluguel.getDataEntrega() != null) {
			Calendar dataEntregaInicial = Calendar.getInstance();
			dataEntregaInicial.setTime(filtroAluguel.getDataEntrega());
			dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaInicial.set(Calendar.MINUTE, 0);
			dataEntregaInicial.set(Calendar.SECOND, 0);
			
			Calendar dataEntregaFinal = Calendar.getInstance();
			dataEntregaFinal.setTime(filtroAluguel.getDataEntrega());
			dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataEntregaFinal.set(Calendar.MINUTE, 59);
			dataEntregaFinal.set(Calendar.SECOND, 59);
			
			query.setParameter("dataEntregaInicial", dataEntregaInicial.getTime());
			query.setParameter("dataEntregaFinal", dataEntregaFinal.getTime());
		}
		
		if (filtroAluguel.getDataDevolucao() != null) {
			Calendar dataDevolucaoInicial = Calendar.getInstance();
			dataDevolucaoInicial.setTime(filtroAluguel.getDataDevolucao());
			dataDevolucaoInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataDevolucaoInicial.set(Calendar.MINUTE, 0);
			dataDevolucaoInicial.set(Calendar.SECOND, 0);
			
			Calendar dataDevolucaoFinal = Calendar.getInstance();
			dataDevolucaoFinal.setTime(filtroAluguel.getDataDevolucao());
			dataDevolucaoFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataDevolucaoFinal.set(Calendar.MINUTE, 59);
			dataDevolucaoFinal.set(Calendar.SECOND, 59);
			
			query.setParameter("dataDevolucaoInicial", dataDevolucaoInicial.getTime());
			query.setParameter("dataDevolucaoFinal", dataDevolucaoFinal.getTime());
		}
		
		if (filtroAluguel.getCarro().getModelo() != null) {
			query.setParameter("modelo", filtroAluguel.getCarro().getModelo());
		}
		
		return query.getResultList();
	}
	
	/*
	 * Consulta utilizando a implementação do Hibernate
	 */
	@SuppressWarnings("unchecked")
	public List<Aluguel>buscarPorDataDeEntregaEModeloCarroCriteria(FiltroAluguel filtroAluguel){
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Aluguel.class);
		
		if(filtroAluguel.getDataEntrega()!= null){
			criteria.add(Restrictions.between("dataEntrega", geraDataInicial(filtroAluguel.getDataEntrega()), 
												geraDataFinal(filtroAluguel.getDataEntrega())));
		}
		if(filtroAluguel.getDataDevolucao() != null){
			criteria.add(Restrictions.between("dataEntrega", geraDataInicial(filtroAluguel.getDataDevolucao()), 
					geraDataFinal(filtroAluguel.getDataDevolucao())));
		}
		if(filtroAluguel.getCarro().getModelo() != null){
			criteria.createAlias("carro", "c");
			criteria.add(Restrictions.eq("c.modelo", filtroAluguel.getCarro().getModelo()));
		}
		return criteria.list();
	}
	
	public BigDecimal calcularTotalDoMesDe(int mes){
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Aluguel.class);
		
		criteria.setProjection(Projections.sum("valorTotal"));
		criteria.add(Restrictions.sqlRestriction("month(dataPedido) = ?", mes, StandardBasicTypes.INTEGER));
		
		return (BigDecimal)criteria.uniqueResult();
	}
	
	private Date geraDataInicial(Date dataEntrega) {
		Calendar dataEntregaInicial = Calendar.getInstance();
		dataEntregaInicial.setTime(dataEntrega);
		dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
		dataEntregaInicial.set(Calendar.MINUTE, 0);
		dataEntregaInicial.set(Calendar.SECOND, 0);
		
		return dataEntregaInicial.getTime();
	}
	
	private Date geraDataFinal(Date dataEntrega) {
		Calendar dataEntregaFinal = Calendar.getInstance();
		dataEntregaFinal.setTime(dataEntrega);
		dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 23);
		dataEntregaFinal.set(Calendar.MINUTE, 59);
		dataEntregaFinal.set(Calendar.SECOND, 59);
		
		return dataEntregaFinal.getTime();
	}

}