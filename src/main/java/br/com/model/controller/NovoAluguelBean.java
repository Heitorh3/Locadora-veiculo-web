package br.com.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.model.dao.CarroDAO;
import br.com.model.dao.MotoristaDAO;
import br.com.model.modelo.Aluguel;
import br.com.model.modelo.ApoliceSeguro;
import br.com.model.modelo.Carro;
import br.com.model.modelo.Motorista;
import br.com.model.service.CadastroAluguelService;
import br.com.model.service.NegocioException;
import br.com.model.util.jsf.FacesUtil;

@Named
@ViewScoped
public class NovoAluguelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aluguel aluguel;
	
	private List<Carro> carros;
	
	@Inject
	private CadastroAluguelService cadastroAluguelService;
	
	@Inject
	private CarroDAO carroDAO;
	
	@Inject
	private MotoristaDAO motoristaDAO;
	
	private List<Motorista> motoristas;
	
	public void salvar() {
		try {
			this.cadastroAluguelService.salvar(aluguel);
			FacesUtil.addSuccessMessage("Aluguel salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
		this.limpar();
	}
	
	@PostConstruct
	public void inicializar() {
		this.limpar();
		
		this.carros = this.carroDAO.buscarTodos();
		this.motoristas = this.motoristaDAO.buscarTodos();
	}
	
	public void limpar() {
		this.aluguel = new Aluguel();
		this.aluguel.setApoliceSeguro(new ApoliceSeguro());
	}

	public Aluguel getAluguel() {
		return aluguel;
	}
	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}

	public List<Carro> getCarros() {
		return carros;
	}
	
	public List<Motorista> getMotoristas() {
	    return motoristas;
	}

}
