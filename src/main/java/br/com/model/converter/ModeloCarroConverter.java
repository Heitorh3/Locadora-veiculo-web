package br.com.model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.model.dao.ModeloCarroDAO;
import br.com.model.modelo.ModeloCarro;
import br.com.model.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=ModeloCarro.class)
public class ModeloCarroConverter implements Converter {

	private ModeloCarroDAO modeloCarroDAO;
	
	public ModeloCarroConverter() {
		this.modeloCarroDAO = CDIServiceLocator.getBean(ModeloCarroDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ModeloCarro retorno = null;

		if (value != null) {
			retorno = this.modeloCarroDAO.buscarPeloCodigo(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((ModeloCarro) value).getCodigo();
			return codigo == null ? null : codigo.toString();
		}
		return "";
	}

}