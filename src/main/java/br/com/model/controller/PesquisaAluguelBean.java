package br.com.model.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.AluguelDAO;
import br.com.model.dao.ModeloCarroDAO;
import br.com.model.filter.FiltroAluguel;
import br.com.model.modelo.Aluguel;
import br.com.model.modelo.Carro;
import br.com.model.modelo.ModeloCarro;

@Named
@ViewScoped
public class PesquisaAluguelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ModeloCarro> modelosCarros;
	
	@Inject
	private FiltroAluguel filtroAluguel;
	
	private List<Aluguel> alugueis;
	
	@Inject
	private ModeloCarroDAO modeloCarroDAO;
	
	@Inject
	private AluguelDAO aluguelDAO;
	
	@PostConstruct
	public void inicializar() {
		this.filtroAluguel.setCarro(new Carro());
		this.modelosCarros = this.modeloCarroDAO.buscarTodos();
		
		this.alugueis = new ArrayList<>();
	}
	
	public void pesquisar() {
		this.alugueis = aluguelDAO.buscarPorDataDeEntregaEModeloCarro(filtroAluguel);
	}
	
	public List<ModeloCarro> getModelosCarros() {
		return modelosCarros;
	}

	public List<Aluguel> getAlugueis() {
		return alugueis;
	}

	public FiltroAluguel getFiltroAluguel() {
		return filtroAluguel;
	}

	public void setFiltroAluguel(FiltroAluguel filtroAluguel) {
		this.filtroAluguel = filtroAluguel;
	}

}