package br.com.model.dao;


import java.io.Serializable;
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

import br.com.model.modelo.Aluguel;
import br.com.model.modelo.ModeloCarro;


public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public void salvar(Aluguel aluguel) {
		manager.merge(aluguel);
	}

	public List<Aluguel> buscarPorDataDeEntregaEModeloCarro(Date dataEntrega, Date dataDevolucao,
			ModeloCarro modelo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluguel> criteriaQuery = builder.createQuery(Aluguel.class);
		Root<Aluguel> a = criteriaQuery.from(Aluguel.class);
		criteriaQuery.select(a);
		
		List<Predicate> predicates = new ArrayList<>();
		if (dataEntrega != null) {
			ParameterExpression<Date> dataEntregaInicial = builder.parameter(Date.class, "dataEntregaInicial");
			ParameterExpression<Date> dataEntregaFinal = builder.parameter(Date.class, "dataEntregaFinal");
			predicates.add(builder.between(a.<Date>get("dataEntrega"), dataEntregaInicial, dataEntregaFinal));
		}
		
		if (dataDevolucao != null) {
			ParameterExpression<Date> dataDevolucaoInicial = builder.parameter(Date.class, "dataDevolucaoInicial");
			ParameterExpression<Date> dataDevolucaoFinal = builder.parameter(Date.class, "dataDevolucaoFinal");
			predicates.add(builder.between(a.<Date>get("dataDevolucao"), dataDevolucaoInicial, dataDevolucaoFinal));
		}
		
		if (modelo != null) {
			ParameterExpression<ModeloCarro> modeloExpression = builder.parameter(ModeloCarro.class, "modelo");
			predicates.add(builder.equal(a.get("carro").get("modelo"), modeloExpression));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Aluguel> query = manager.createQuery(criteriaQuery);
		
		if (dataEntrega != null) {
			Calendar dataEntregaInicial = Calendar.getInstance();
			dataEntregaInicial.setTime(dataEntrega);
			dataEntregaInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataEntregaInicial.set(Calendar.MINUTE, 0);
			dataEntregaInicial.set(Calendar.SECOND, 0);
			
			Calendar dataEntregaFinal = Calendar.getInstance();
			dataEntregaFinal.setTime(dataEntrega);
			dataEntregaFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataEntregaFinal.set(Calendar.MINUTE, 59);
			dataEntregaFinal.set(Calendar.SECOND, 59);
			
			query.setParameter("dataEntregaInicial", dataEntregaInicial.getTime());
			query.setParameter("dataEntregaFinal", dataEntregaFinal.getTime());
		}
		
		if (dataDevolucao != null) {
			Calendar dataDevolucaoInicial = Calendar.getInstance();
			dataDevolucaoInicial.setTime(dataDevolucao);
			dataDevolucaoInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataDevolucaoInicial.set(Calendar.MINUTE, 0);
			dataDevolucaoInicial.set(Calendar.SECOND, 0);
			
			Calendar dataDevolucaoFinal = Calendar.getInstance();
			dataDevolucaoFinal.setTime(dataDevolucao);
			dataDevolucaoFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataDevolucaoFinal.set(Calendar.MINUTE, 59);
			dataDevolucaoFinal.set(Calendar.SECOND, 59);
			
			query.setParameter("dataDevolucaoInicial", dataDevolucaoInicial.getTime());
			query.setParameter("dataDevolucaoFinal", dataDevolucaoFinal.getTime());
		}
		
		if (modelo != null) {
			query.setParameter("modelo", modelo);
		}
		
		return query.getResultList();
	}

}