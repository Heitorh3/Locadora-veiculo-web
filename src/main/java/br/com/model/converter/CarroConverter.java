package br.com.model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.model.dao.CarroDAO;
import br.com.model.modelo.Carro;
import br.com.model.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Carro.class)
public class CarroConverter implements Converter {

	private CarroDAO carroDAO;
	
	public CarroConverter() {
		this.carroDAO = CDIServiceLocator.getBean(CarroDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Carro retorno = null;

		if (value != null) {
			retorno = this.carroDAO.buscarPeloCodigo(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Carro) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}