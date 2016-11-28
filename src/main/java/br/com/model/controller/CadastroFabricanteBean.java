package br.com.model.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.modelo.Fabricante;
import br.com.model.service.CadastroFabricanteService;
import br.com.model.service.NegocioException;
import br.com.model.util.cdi.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFabricanteBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroFabricanteService cadastroFabricanteService;

	@Inject
	private Fabricante fabricante;
	
	public void salvar() {
		try {
			this.cadastroFabricanteService.salvar(fabricante);
			FacesUtil.addSuccessMessage("Fabricante salvo com sucesso!");
			
			this.limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	@PostConstruct
	public void init() {
		this.limpar();
	}
	
	public void limpar() {
		this.fabricante = new Fabricante();
	}

	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
}
