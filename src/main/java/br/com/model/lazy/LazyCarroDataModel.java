package br.com.model.lazy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.Carro;

public class LazyCarroDataModel extends LazyDataModel<Carro> implements Serializable {

	private static final long serialVersionUID = 1L;

	private CarroDAO carroDAO;
	
	public LazyCarroDataModel(CarroDAO carroDAO) {
		this.carroDAO = carroDAO;
	}
	
	@Override
	public List<Carro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		List<Carro> carros = this.carroDAO.buscaComPaginacao(first, pageSize);
		
		this.setRowCount(this.carroDAO.encontrarQuantidadeDeCarros().intValue());
		
		return carros;
	}
}
