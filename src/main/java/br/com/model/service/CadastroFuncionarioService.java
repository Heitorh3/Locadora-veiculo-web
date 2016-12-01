package br.com.model.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.model.dao.FuncionarioDAO;
import br.com.model.modelo.Funcionario;
import br.com.model.util.jpa.Transacional;

public class CadastroFuncionarioService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioDAO funcionarioDAO;
	
	@Transacional
	public void salvar(Funcionario funcionario)throws NegocioException {
		if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) { 
			throw new NegocioException("O nome do funcionario é obrigatório");
		}
		
		funcionarioDAO.salvar(funcionario);
	}

}
