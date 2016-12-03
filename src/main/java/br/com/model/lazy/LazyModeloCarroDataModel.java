package br.com.model.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.model.dao.ModeloCarroDAO;
import br.com.model.modelo.ModeloCarro;

public class LazyModeloCarroDataModel  extends LazyDataModel<ModeloCarro> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ModeloCarroDAO modeloCarroDAO;
	
	public LazyModeloCarroDataModel(ModeloCarroDAO modeloCarroDAO) {
		this.modeloCarroDAO = modeloCarroDAO;
	}
	
	@Override
	public List<ModeloCarro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<ModeloCarro> modelos = modeloCarroDAO.buscaComPaginacao(first, pageSize);
				
		this.setRowCount(modeloCarroDAO.encontrarQuantidadeDeModelosDeCarro().intValue());
		
		return modelos;
	}

}
