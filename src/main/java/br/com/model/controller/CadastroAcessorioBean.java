package br.com.model.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.modelo.Acessorio;
import br.com.model.service.CadastroAcessorioService;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAcessorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Acessorio acessorio;
	
	@Inject
	private CadastroAcessorioService cadastroAcessorioService;
	
	public CadastroAcessorioBean() {
		this.limpar();
	}
	
	public void salvar() {
		try {
			this.cadastroAcessorioService.salvar(acessorio);
			FacesUtil.addSuccessMessage("Acessório salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
		this.limpar();
	}
	
	public void limpar() {
		this.acessorio = new Acessorio();
	}

	public Acessorio getAcessorio() {
		return acessorio;
	}
	public void setAcessorio(Acessorio acessorio) {
		this.acessorio = acessorio;
	}
	
}