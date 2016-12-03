package br.com.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.ModeloCarroDAO;
import br.com.model.lazy.LazyModeloCarroDataModel;
import br.com.model.modelo.ModeloCarro;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaModeloCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<ModeloCarro> modelosCarro;
	
	private ModeloCarro modeloCarroSelecionado;
	
	@Inject
	ModeloCarroDAO modeloCarroDAO;
	
	private LazyModeloCarroDataModel lazyModelos;

	public List<ModeloCarro> getModelosCarro() {
		return modelosCarro;
	}
	
	@PostConstruct
	public void inicializar() {
		//this.modelosCarro = this.modeloCarroDAO.buscarTodos();
		this.lazyModelos = new LazyModeloCarroDataModel(modeloCarroDAO);
	}
	
	public void excluir() {
		try {
			modeloCarroDAO.excluir(modeloCarroSelecionado);
			this.modelosCarro.remove(modeloCarroSelecionado);
			FacesUtil.addSuccessMessage("Modelo " + modeloCarroSelecionado.getDescricao() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public ModeloCarro getModeloCarroSelecionado() {
		return modeloCarroSelecionado;
	}
	public void setModeloCarroSelecionado(ModeloCarro modeloCarroSelecionado) {
		this.modeloCarroSelecionado = modeloCarroSelecionado;
	}

	public LazyModeloCarroDataModel getLazyModelos() {
		return lazyModelos;
	}
	
}