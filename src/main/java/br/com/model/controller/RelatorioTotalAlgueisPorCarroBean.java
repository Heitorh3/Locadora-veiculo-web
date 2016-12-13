package br.com.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.util.TotalDeAlugueisPorCarro;


@Named
@ViewScoped
public class RelatorioTotalAlgueisPorCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CarroDAO carroDAO;
	
	private List<TotalDeAlugueisPorCarro> totalDeAlugueisPorCarro;
	
	public void buscar() {
		totalDeAlugueisPorCarro = this.carroDAO.buscarTotalAlugueisPorCarro();
	}

	public List<TotalDeAlugueisPorCarro> getTotalDeAlugueisPorCarro() {
		return totalDeAlugueisPorCarro;
	}
	
}
