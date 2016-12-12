package br.com.model.service;

import java.io.Serializable;
import java.util.Calendar;

import javax.inject.Inject;

import br.com.model.dao.AluguelDAO;
import br.com.model.modelo.Aluguel;
import br.com.model.util.jpa.Transacional;

public class CadastroAluguelService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AluguelDAO aluguelDAO;
	
	@Transacional
	public void salvar(Aluguel aluguel) throws NegocioException {
		
		/*if (aluguel.getCarro() == null) {
			throw new NegocioException("O carro é obrigatório");
		}
		*/
		aluguel.setDataPedido(Calendar.getInstance());
		
		this.aluguelDAO.salvar(aluguel);
	}

}