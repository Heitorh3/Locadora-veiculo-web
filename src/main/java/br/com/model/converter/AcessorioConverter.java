package br.com.model.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.model.dao.AcessorioDAO;
import br.com.model.modelo.Acessorio;
import br.com.model.util.cdi.CDIServiceLocator;

@FacesConverter("acessorioConverter")
public class AcessorioConverter implements Converter {

	private AcessorioDAO acessorioDAO;
	
	public AcessorioConverter() {
		this.acessorioDAO = CDIServiceLocator.getBean(AcessorioDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Acessorio retorno = null;
		
		if (value != null) {
			retorno = this.acessorioDAO.buscarPeloCodigo(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Acessorio) value).getCodigo();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}