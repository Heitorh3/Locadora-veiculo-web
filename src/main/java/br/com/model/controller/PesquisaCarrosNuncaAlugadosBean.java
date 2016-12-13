package br.com.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.Carro;

@Named
@ViewScoped
public class PesquisaCarrosNuncaAlugadosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Carro> carros;
	
	@Inject
	private CarroDAO carroDAO;
	
	public void buscarCarrosNuncaAlugados() {
		this.carros = this.carroDAO.buscarCarrosNuncaAlugados();
	}

	public List<Carro> getCarros() {
		return carros;
	}
	
}