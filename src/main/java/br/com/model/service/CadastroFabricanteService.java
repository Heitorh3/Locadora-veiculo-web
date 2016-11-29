package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.model.dao.FabricanteDAO;
import br.com.model.modelo.Fabricante;
import br.com.model.util.jpa.Transacional;

public class CadastroFabricanteService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private FabricanteDAO fabricanteDao;
	
	@Transacional
	public void salvar(Fabricante fabricante)throws NegocioException {
		if (fabricante.getNome() == null || fabricante.getNome().trim().equals("")) { 
			throw new NegocioException("O nome do fabricante é obrigatório");
		}
		
		this.fabricanteDao.salvar(fabricante);
	}
}
