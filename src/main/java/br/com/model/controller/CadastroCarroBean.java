package br.com.model.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.model.dao.AcessorioDAO;
import br.com.model.dao.ModeloCarroDAO;
import br.com.model.modelo.Acessorio;
import br.com.model.modelo.Carro;
import br.com.model.modelo.ModeloCarro;
import br.com.model.service.CadastroCarroService;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCarroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Carro carro;

	private List<ModeloCarro> modelosCarros;
	
	private List<Acessorio> acessorios;
	
	@Inject
	private CadastroCarroService cadastroCarroService;
	
	@Inject
	private AcessorioDAO acessorioDAO;
	
	@Inject
	private ModeloCarroDAO modeloCarroDAO;
	
	private UploadedFile uploadedFile;
	
	@PostConstruct
	public void inicializar() {
		this.limpar();
		
		this.acessorios = acessorioDAO.buscarTodos();
		this.modelosCarros = this.modeloCarroDAO.buscarTodos();
	}
	
	public void salvar() {
		try {
			if (this.uploadedFile != null) {
				System.out.println("FOTO " + uploadedFile);
				this.carro.setFoto(this.uploadedFile.getContents());
			}
			this.cadastroCarroService.salvar(carro);
			FacesUtil.addSuccessMessage("Carro salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro desconhecido. Contatar o administrador");
		}
		
		this.limpar();
	}
	
	public void limpar() {
		this.carro = new Carro();
		this.carro.setAcessorios(new ArrayList<Acessorio>());
	}

	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public List<ModeloCarro> getModelosCarros() {
		return modelosCarros;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}