package br.com.model.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.model.dao.CarroDAO;
import br.com.model.lazy.LazyCarroDataModel;
import br.com.model.modelo.Carro;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@SessionScoped
public class PesquisaCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CarroDAO carroDAO;
	
	private List<Carro> carros = new ArrayList<>();
	
	private Carro carroSelecionado;
	
	private LazyCarroDataModel lazyCarros;
	
	private DefaultStreamedContent content;
	
	public List<Carro> getCarros() {
		return carros;
	}
	
	public void excluir() {
		try {
			carroDAO.excluir(carroSelecionado);
			this.carros.remove(carroSelecionado);
			FacesUtil.addSuccessMessage("Carro placa " + carroSelecionado.getPlaca() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Carro getCarroSelecionado() {
		return carroSelecionado;
	}
	public void setCarroSelecionado(Carro carroSelecionado) {
		this.carroSelecionado = carroSelecionado;
	}
	
	public void buscarCarroComAcessorios(){
		this.carroSelecionado = carroDAO.buscarCarroComAcessorios(carroSelecionado.getCodigo());
	}
	
	public StreamedContent getContent(){
		if(this.carroSelecionado != null){
			byte[] imagem = this.carroSelecionado.getFoto();
			content = new DefaultStreamedContent(new ByteArrayInputStream(imagem), "image/png", "teste.png");
			return content;
		}
		return null;
	}

	@PostConstruct
	public void inicializar() {
		//carros = carroDAO.buscarTodos();
		lazyCarros = new LazyCarroDataModel(carroDAO);
	}

	public LazyCarroDataModel getLazyCarros() {
		return lazyCarros;
	}
	
}
