package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.model.dao.ModeloCarroDAO;
import br.com.model.modelo.ModeloCarro;
import br.com.model.util.jpa.Transacional;

public class CadastroModeloCarroService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private ModeloCarroDAO modeloCarroDAO;
	
	@Transacional
	public void salvar(ModeloCarro modeloCarro)throws NegocioException {
		if (modeloCarro.getDescricao() == null || modeloCarro.getDescricao().trim().equals("")) {
			throw new NegocioException("O nome do modelo é obrigatório.");
		}
		
		if (modeloCarro.getFabricante() == null) {
			throw new NegocioException("O fabricante e obrigatório");
		}
		
		this.modeloCarroDAO.salvar(modeloCarro);
	}
}
