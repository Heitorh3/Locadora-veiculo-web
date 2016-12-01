package br.com.model.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.MotoristaDAO;
import br.com.model.modelo.Motorista;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaMotoristaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	MotoristaDAO motoristaDAO;
	
	private List<Motorista> motoristas = new ArrayList<>();
	
	private Motorista motoristaSelecionado;
	
	public List<Motorista> getMotoristas() {
		return motoristas;
	}
	
	public void excluir() {
		try {
			motoristaDAO.excluir(motoristaSelecionado);
			this.motoristas.remove(motoristaSelecionado);
			FacesUtil.addSuccessMessage("Motorista " + motoristaSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public Motorista getMotoristaSelecionado() {
		return motoristaSelecionado;
	}
	public void setMotoristaSelecionado(Motorista motoristaSelecionado) {
		this.motoristaSelecionado = motoristaSelecionado;
	}
	
	@PostConstruct
	public void inicializar() {
		motoristas = motoristaDAO.buscarTodos();
	}
}
