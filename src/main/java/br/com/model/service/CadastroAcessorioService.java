package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.model.dao.AcessorioDAO;
import br.com.model.modelo.Acessorio;

public class CadastroAcessorioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AcessorioDAO acessorioDAO;
	
	@Transactional
	public void salvar(Acessorio acessorio) throws NegocioException {
		
		if (acessorio.getDescricao() == null || acessorio.getDescricao().trim().equals("")) {
			throw new NegocioException("A descrição do acessório é obrigatório");
		}
		
		this.acessorioDAO.salvar(acessorio);
	}

}	